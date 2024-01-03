package com.example.joosulsa.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.CheckPopupActivity;
import com.example.joosulsa.LoginActivity;
import com.example.joosulsa.QuizActivity;
import com.example.joosulsa.QuizClosePopup;
import com.example.joosulsa.R;
import com.example.joosulsa.SearchActivity;
import com.example.joosulsa.TestActivity;
import com.example.joosulsa.category.MainCategoryAdapter;
import com.example.joosulsa.category.MainCategoryVO;
import com.example.joosulsa.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class HomeFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private ActivityResultLauncher<Intent> cameraLauncher;

    // sharedPreference 선언(자동 로그인에 씀)
    private SharedPreferences preferences;

    // 카테고리 데이터 연결
    private ArrayList<MainCategoryVO> dataset;
    // 카테고리 어댑터 연결
    private MainCategoryAdapter adapter;

    private RequestQueue requestQueue;

    // 스프링 url 관리 여기 몰아서 할거임
    private String quizUrl = "http://192.168.219.62:8089/quizRequest";
    private String checkUrl = "http://192.168.219.62:8089/dataCheck";

    int postMethod = Request.Method.POST;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // binding으로 view내부 객체들 가져다 쓰려고 선언하는 코드임
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);

        // requestqueue없으면 만드는곳
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        // 자동 로그인 부분
        // 데이터 가져오기
        String autoId = autoIdMethod(preferences);
        String autoPw = autoPwMethod(preferences);
        String autoName = autoNameMethod(preferences);
        String autoAddr = autoAddrMethod(preferences);
        String autoNick = autoNickMethod(preferences);
        boolean checkBoolean = true;
        boolean quizBoolean = true;
        int userPoint = 0;
        if (autoId!=null){
            quizCheckDataRequest(autoId);
            checkBoolean = autoTodayAtt(preferences);
            quizBoolean = autoQuiz(preferences);
            userPoint = userPoint(preferences);
        }
        Log.d("checkingchecking", "id:"+autoId + "닉네임:"+autoNick+"pw:"+autoPw + "이름" + autoName + "주소 : " + autoAddr);
        Log.d("booleanCheck", checkBoolean + " / " + quizBoolean);

        // 출석체크
//        if (checkBoolean == false){
//
//            Intent intent = new Intent(getActivity(), CheckPopupActivity.class);
//
//            startActivity(intent);
//        }else {
//
//        }

        // homeFragment에 있는 요소 순서대로 이벤트 작성바람

        // 검색창 이벤트
        binding.search.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        Intent afterUserInputIntent = getActivity().getIntent();
        String checkUserInputNick = afterUserInputIntent.getStringExtra("userNick");

        // 회원정보창 이벤트
        if (autoId!=null && autoPw!=null) {
            binding.memberId.setText(autoNick);
            binding.numPoint.setText(Integer.toString(userPoint));
            binding.memberInfo.setOnClickListener(v -> {
                // MyPageFragment로 이동하는 코드
                requireActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.fl,
                        new MypageFragment()
                ).commit();
            });
        } else if (checkUserInputNick!=null) {
            binding.memberId.setText(checkUserInputNick);
            binding.numPoint.setText(Integer.toString(userPoint));
            binding.memberInfo.setOnClickListener(v -> {
                // MyPageFragment로 이동하는 코드
                requireActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.fl,
                        new MypageFragment()
                ).commit();
            });
        } else{
            binding.numPoint.setText(Integer.toString(userPoint));
            binding.memberInfo.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        }

        // 카메라 이벤트
        binding.checkBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 카메라 권한이 없을 경우 권한 요청
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                // 이미 권한이 있는 경우 카메라 앱을 실행하는 코드
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);
            }
        });

        // 퀴즈버튼 이벤트
        if (quizBoolean == false){
            binding.quizBtn.setOnClickListener(v -> {
                int quizNumber = getRandomNumber(1, 100);
                quizRequest(quizNumber);
            });
        }else {
            binding.quizBtn.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), QuizClosePopup.class);
                startActivity(intent);
            });
        }

        // 객체 생성
        dataset = new ArrayList<>();

        // 데이터 넣어주기
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test01"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test02"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test03"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test04"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test05"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test06"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test07"));

        // 레이아웃 보여주기
        // manager : 레이아웃 정보를 갖고있음..
        //LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false); // 가로로 배열해주기

        // 뿌려줄 xml파일에 recycleview 넣어주고 id 값을 꼭 지정해주기
        // 레이아웃을 설정해주기
        binding.categoryList.setLayoutManager(manager);

        // 어댑터를 연결해주기
        adapter = new MainCategoryAdapter(dataset);
        binding.categoryList.setAdapter(adapter); // setAdapter : 이걸 어댑터에 넣어준다고

        // ActivityResultCallback 정의
        ActivityResultCallback<ActivityResult> resultCallback = result -> {
            if (result.getResultCode() == RESULT_OK) {
                // 카메라 앱에서 이미지를 성공적으로 캡처한 경우
                Intent data = result.getData();
                if (data != null && data.getExtras() != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    // 여기에서 캡처한 이미지를 처리하면 됩니다.
                    // 그렇다네
                    Intent testIntent = new Intent(getActivity(), TestActivity.class);
                    testIntent.putExtra("TestImg", bitmap);
                    startActivity(testIntent);
                }
            } else {
                // 캡처가 취소되거나 실패한 경우에 대한 처리
                Toast.makeText(requireContext(), "캡처가 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
        };

        // ActivityResultCallback을 등록
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);

        return binding.getRoot();
    }

    // 자동로그인 유저 출석, 퀴즈 여부 불러오기
    private void quizCheckDataRequest(String autoId){
        StringRequest request = new StringRequest(
                postMethod,
                checkUrl,
                new Response.Listener<String>() {
                    // 서버 통신 성공시
                    @Override
                    public void onResponse(String response) {
                        Log.d("dailyDatacheck", response);
                        handleDailyData(response);
                    }
                    // 서버 통신 실패시
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", autoId);
                return params;
            }
        };
        requestQueue.add(request);
    }

    // 퀴즈 출석 일일데이터 관리
    private void handleDailyData(String response){

        try {
            JSONObject jsonResponse = new JSONObject(response);
            Log.d("why", response);
            preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
            boolean todayAtt = jsonResponse.getBoolean("attendance");
            boolean quizAtt = jsonResponse.getBoolean("quizParticipation");
            int monthlyAttNum = Integer.parseInt(jsonResponse.getString("monthlyAttendance"));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("quizBoolean", todayAtt);
            editor.putBoolean("checkBoolean", quizAtt);
            editor.putInt("monthlyAttendance", monthlyAttNum);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // 공유 프리퍼런스 데이터 호출
    private String autoIdMethod(SharedPreferences preferences){
        Log.d("autoIdCheck", "Asdasd");
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoId = preferences.getString("autoId", null);
        // Log.d("casdas", autoId);
        return autoId;
    }

    private String autoPwMethod(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoPw = preferences.getString("autoPw", null);
        return autoPw;
    }

    private String autoNameMethod(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoName = preferences.getString("autoName", null);
        return autoName;
    }

    private String autoAddrMethod(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoAddr = preferences.getString("autoAddr", null);
        return autoAddr;
    }

    private String autoNickMethod(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoNick = preferences.getString("autoNick", null);
        return autoNick;
    }

    private boolean autoTodayAtt(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        boolean checkBoolean = preferences.getBoolean("checkBoolean", false);
        return checkBoolean;
    }

    private boolean autoQuiz(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        boolean quizBoolean = preferences.getBoolean("quizBoolean", false);
        return quizBoolean;
    }

    private int userPoint(SharedPreferences preferences){
        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int userPoint = preferences.getInt("userPoint", 0);
        return userPoint;
    }

//    private void setResetWorker() {
//        // Constraints 설정
//        Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .build();
//
//        // 12시에 실행되도록 설정
//        Calendar now = Calendar.getInstance();
//        now.set(Calendar.HOUR_OF_DAY, 12);
//        now.set(Calendar.MINUTE, 0);
//        now.set(Calendar.SECOND, 0);
//        long delayInMillis = now.getTimeInMillis() - System.currentTimeMillis();
//
//        // PeriodicWorkRequest 설정(worker 요청 주기 설정하는거)
//        PeriodicWorkRequest resetWorkerRequest = new PeriodicWorkRequest.Builder(
//                ResetWorker.class,
//                1,
//                TimeUnit.DAYS
//        )
//                .setConstraints(constraints)
//                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
//                .build();
//
//        // WorkManager에 작업 추가
//        WorkManager.getInstance(requireContext()).enqueue(resetWorkerRequest);
//    }

    // 출석 현황 가져오는 메소드(출석 팝업에 띄워줄 데이터 가져오는거)
    private void userDateCheck(){

    }

    // 랜덤 숫자 생성 메소드(퀴즈 번호 호출할때 쓰는거)
    private int getRandomNumber(int min, int max) {
        Random rd = new Random();
        return rd.nextInt(max - min + 1) + min;
    }

    // 퀴즈 번호 랜덤으로 뽑아서 퀴즈 정보 불러오는 메소드
    private void quizRequest(int quizNumber){
        StringRequest request = new StringRequest(
                postMethod,
                quizUrl,
                new Response.Listener<String>() {
                    // 서버 통신 성공시
                    @Override
                    public void onResponse(String response) {
                        Log.d("quizdatacheck", response);
                        handleQuizLoad(response);
                    }
                    // 서버 통신 실패시
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("quizNum", Integer.toString(quizNumber));
                return params;
            }
        };
        requestQueue.add(request);
    }

    // 스프링에서 받아온 퀴즈 json데이터 처리 메소드임
    private void handleQuizLoad(String response) {
        // 서버에서 받아온 응답을 처리하는 로직을 작성
        // response는 서버에서 보낸 JSON 형태의 데이터일 것이므로, 필요에 따라 파싱하여 사용
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String quizContent = jsonResponse.getString("quizContent");
            String quizAnswer = jsonResponse.getString("quizAnswer");
            String quizInfo = jsonResponse.getString("quizInfo");
            int quizPoint = jsonResponse.getInt("quizPoint");
            int quizNum = jsonResponse.getInt("quizNum");
            Log.d("QuizDataCheck", quizContent + "/" + quizAnswer + "/" + quizInfo + "/" + quizPoint + "/" + quizNum);
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putExtra("quizContent", quizContent);
            intent.putExtra("quizAnswer", quizAnswer);
            intent.putExtra("quizInfo", quizInfo);
            intent.putExtra("quizPoint", quizPoint);
            intent.putExtra("quizNum", quizNum);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}