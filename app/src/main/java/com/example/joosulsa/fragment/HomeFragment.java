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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.CheckActivity;
import com.example.joosulsa.LoginActivity;
import com.example.joosulsa.QuizActivity;
import com.example.joosulsa.QuizClosePopup;
import com.example.joosulsa.R;
import com.example.joosulsa.RecycleDetailActivity;
import com.example.joosulsa.SearchActivity;
import com.example.joosulsa.SearchDetailActivity;
import com.example.joosulsa.TestActivity;
import com.example.joosulsa.TownRankActivity;
import com.example.joosulsa.category.MainCategoryAdapter;
import com.example.joosulsa.category.MainCategoryVO;
import com.example.joosulsa.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


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
    private String cateUrl = "http://192.168.219.62:8089/category";
    private String falseUrl = "http://192.168.219.62:8089/makeFalse";
    private String trueUrl = "http://192.168.219.62:8089/makeTrue";
    // 가장 많이 찾고 있어요에 조회수 순으로 데이터 넣어주는 주소
    private String mainViewsUrl = "http://192.168.219.42:8089/mainViews";
    // 해시태그에서 해당하는 재활용 페이지로 넘어갈때 요청 주소
    private String hashtagUrl = "http://192.168.219.42:8089/hashtag";


    int postMethod = Request.Method.POST;

    // recyclerview
    private RecyclerView categoryView;


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
        boolean checkBoolean;
        boolean quizBoolean;
        int userPoint = 0;
        quizCheckDataRequest(autoId);
        checkBoolean = autoTodayAtt(preferences);
        quizBoolean = autoQuiz(preferences);
        Log.d("daxczxzcqwe", Integer.toString(userPoint(preferences)));
        userPoint = userPoint(preferences);
        Log.d("대체 왜 초기화가 안되냐", String.valueOf(checkBoolean));
        Log.d("checkingchecking", "id:"+autoId + "닉네임:"+autoNick+"pw:"+autoPw + "이름" + autoName + "주소 : " + autoAddr);
        Log.d("booleanCheck", checkBoolean + " / " + quizBoolean);
        String checkTime;

        long now =System.currentTimeMillis();
        Date today =new Date(now);
        SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd");
        String nowTime = format.format(today);
        Log.d("nowTime", nowTime);

        // 출석체크
//        if (checkBoolean == false){
//
//            Intent intent = new Intent(getActivity(), CheckPopupActivity.class);
//
//            long now1 =System.currentTimeMillis();
//            Date today1 =new Date(now1);
//            SimpleDateFormat format1 =new SimpleDateFormat("yyyy.MM.dd");
//            checkTime = format1.format(today1);
//            Log.d("checkTime", checkTime);
//            if (checkTime.equals(nowTime)){
//                makeTrue(autoId);
//                quizCheckDataRequest(autoId);
//            }else {
//                makeFalse(autoId);
//
//            }
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


        // 가장 많이 찾고 있어요 조회수 값 가져오는 메소드
        mainViews();

        // 가장 많이 찾고 있어요 클릭 이벤트

        String trashName2 = binding.hashtag2.getText().toString();
        String trashName3 = binding.hashtag3.getText().toString();
        String trashName4 = binding.hashtag4.getText().toString();


        binding.hashtag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trashName1 = binding.hashtag1.getText().toString();
                Log.d("값 가지고 오니",trashName1);
                hashtagRequest(trashName1);

            }
        });


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


        // 우리동네 랭킹 버튼 이벤트
        binding.rankBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TownRankActivity.class);
            startActivity(intent);
            getActivity().finish();
        });


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

        // 카테고리 코드
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoryView = binding.getRoot().findViewById(R.id.categoryList);
        categoryView.setLayoutManager(manager);
        dataset = new ArrayList<>();
        loadRecyclingItems();
        adapter = new MainCategoryAdapter(dataset);
        binding.categoryList.setAdapter(adapter);

        // 카테고리 영역 클릭 이벤트
        categoryView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = categoryView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null) {
                        int position = categoryView.getChildAdapterPosition(child);
                        // 아이템을 길게 클릭한 경우의 처리 (필요에 따라 구현)
                    }
                }
            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    // 클릭한 카테고리의 정보 가져오기
                    MainCategoryVO clickedCategory = dataset.get(position);
                    String trashName = clickedCategory.getTitle();

                    // RecycleDetailActivity로 이동하는 Intent 생성
                    Intent intent = new Intent(getActivity(), RecycleDetailActivity.class);
                    intent.putExtra("trashName", trashName);
                    startActivity(intent);

                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        // 퀴즈버튼 이벤트
        if (quizBoolean == false){
            binding.quizBtn.setOnClickListener(v -> {
                int quizNumber = getRandomNumber(1, 20);
                quizRequest(quizNumber);
            });
        }else {
            binding.quizBtn.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), QuizClosePopup.class);
                startActivity(intent);
            });
        }

        // 오늘의 출석체크 버튼 이벤트
        binding.calBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CheckActivity.class);
            startActivity(intent);
        });


        return binding.getRoot();
    }

    private void makeTrue(String autoId){
        StringRequest request = new StringRequest(
                postMethod,
                trueUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
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

    private void makeFalse(String autoId){
        StringRequest request = new StringRequest(
                postMethod,
                falseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
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

    // 카테고리에 넣을 데이터 가져옴
    private void loadRecyclingItems() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                postMethod,
                cateUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("cateData", response.toString());
                        int[] cateImgs ={R.drawable.can_icon, R.drawable.glass_icon, R.drawable.paper_icon, R.drawable.plastic_icon,
                                R.drawable.styrofoam_icon, R.drawable.vinyl_icon};
                        try {
                            for (int i = 0; i < 6; i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.d("sbytry", response.getJSONObject(i).toString());
                                String title = jsonObject.getString("trashName");
                                Log.d("titleCheck", title);
                                dataset.add(new MainCategoryVO(cateImgs[i], title));
                            }

                            // RecyclerView에 데이터가 변경되었음을 알림
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
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
            boolean todayAtt = jsonResponse.getBoolean("checkBoolean");
            boolean quizAtt = jsonResponse.getBoolean("quizBoolean");
            int monthlyAttNum = Integer.parseInt(jsonResponse.getString("monthlyAttendance"));
            int totalPoints = Integer.parseInt(jsonResponse.getString("totalPoints"));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("quizBoolean", todayAtt);
            editor.putBoolean("checkBoolean", quizAtt);
            editor.putInt("monthlyAttendance", monthlyAttNum);
            editor.putInt("totalPoints", totalPoints);
            Log.d("preferencePoints", String.valueOf(totalPoints));
            editor.apply();
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
        int userPoint = preferences.getInt("totalPoints", 0);
        Log.d("casdas", Integer.toString(userPoint));
        return userPoint;
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

    // 가장 많이 찾고 있어요 조회수별 요청 및 응답 처리
    private void mainViews(){

        StringRequest request = new StringRequest(
                postMethod,
                mainViewsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("오시나요", response);
                        mainViewsDataHandle(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("안옴", "안온다고");
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                return param;
            }
        };

        requestQueue.add(request);
    }

    private void mainViewsDataHandle(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);

            // 각 trashName의 실제 값 리스트
            List<String> trashNameValueList = new ArrayList<>();

            // JSON 배열을 순회하며 데이터 추출
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject trashNameData = jsonArray.getJSONObject(i);
                String trashNameValue = trashNameData.getString("trashName");


                // Log.d("데이터 확인", trashNameValue.toString());

                // 추출한 데이터를 리스트에 추가
                trashNameValueList.add(trashNameValue);
            }

            Log.d("조회값", trashNameValueList.toString());
            // 각 해시태그에 trashNameValueList에 담긴 값 넣어주기
            TextView trashNameTextView1 = getView().findViewById(R.id.hashtag1);
            TextView trashNameTextView2 = getView().findViewById(R.id.hashtag2);
            TextView trashNameTextView3 = getView().findViewById(R.id.hashtag3);
            TextView trashNameTextView4 = getView().findViewById(R.id.hashtag4);

            trashNameTextView1.setText(trashNameValueList.get(0));
            trashNameTextView2.setText(trashNameValueList.get(1));
            trashNameTextView3.setText(trashNameValueList.get(2));
            trashNameTextView4.setText(trashNameValueList.get(3));

            // 나머지 로직 계속 작성
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // 해시태크 클릭 시 서버 통신
    private void hashtagRequest(String trashName) {


        StringRequest request = new StringRequest(
                postMethod,
                hashtagUrl,
                response -> {
                    // 서버통신 성공시
                    Log.d("해시 태그 통신 성공", response); // 로그
                    handHash(response);

                },
                error -> {
                    // 서버통신 실패시
                    Log.d("해시 태그 통신 실패", "안와용");
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>(); // 안드로이드에서 스프링으로 요청보낼 때 스프링 매개변수로 뭐 보낼지 처리해줌
                params.put("trashName", trashName);
                return params;
            }
        };
        // Request를 RequestQueue에 추가
        requestQueue.add(request);
    }

    // Spring 에서 받아온 재활용 데이터 처리 메소드(해시태그)
    private void handHash(String response) {
//                sepaMethod = 분리수거 방법
//                sepaCaution = 분리수거 주의사항
//                sepaImg = 분리수거 이미지
//                sepaVideo = 분리수거 영상
//                recycleVideo = 업사이클 비디오
//                recycleImg = 업사이클 이미지
        try {
            // 데이터 파싱 작업
            // 데이터 파싱 작업
            JSONObject jsonResponse = new JSONObject(response);

            // searchCheck JSON 객체 가져오기
            JSONObject hashtagCheckObject = jsonResponse.getJSONObject("hashtagCheck");

            Log.d("해시태그 확인",hashtagCheckObject.toString());


            // searchCheck의 속성들 가져오기
            String trashName = hashtagCheckObject.getString("trashName");
            String sepaMethod = hashtagCheckObject.getString("sepaMethod");
            String sepaCaution = hashtagCheckObject.getString("sepaCaution");
            String sepaImg = hashtagCheckObject.getString("sepaImg");
            String sepaVideo = hashtagCheckObject.getString("sepaVideo");
            String recycleVideo = hashtagCheckObject.getString("recycleVideo");
            String recycleImg = hashtagCheckObject.getString("recycleImg");
            String recycleNum = hashtagCheckObject.getString("recycleNum");


            // 확인
            Log.d("데이터 처리", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);

            // Intent에 값 넣어주기
            Intent hashtagIntent = new Intent(getActivity(), RecycleDetailActivity.class);
            hashtagIntent.putExtra("trashName", trashName);
            hashtagIntent.putExtra("sepaMethod",sepaMethod);
            hashtagIntent.putExtra("sepaCaution",sepaCaution);
            hashtagIntent.putExtra("sepaImg",sepaImg);
            hashtagIntent.putExtra("sepaVideo",sepaVideo);
            hashtagIntent.putExtra("recycleVideo",recycleVideo);
            hashtagIntent.putExtra("recycleImg",recycleImg);
            hashtagIntent.putExtra("searchmethod", "text");
            hashtagIntent.putExtra("recyNum", recycleNum);
            Log.d("searchWhy", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);
            startActivity(hashtagIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}