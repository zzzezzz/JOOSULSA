package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.LoginActivity;
import com.example.joosulsa.MainActivity;
import com.example.joosulsa.QuizActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.SearchActivity;
import com.example.joosulsa.category.MainCategoryAdapter;
import com.example.joosulsa.category.MainCategoryVO;
import com.example.joosulsa.databinding.ActivitySearchBinding;
import com.example.joosulsa.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class HomeFragment extends Fragment {

    // 카테고리 데이터 연결
    private ArrayList<MainCategoryVO> dataset;
    // 카테고리 어댑터 연결
    private MainCategoryAdapter adapter;

    private RequestQueue requestQueue;

    // 스프링 url 관리 여기 몰아서 할거임
    private  String quizUrl = "http://192.168.219.44:8089/quizRequest";

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

        // homeFragment에 있는 요소 순서대로 이벤트 작성바람

        // 검색창 이벤트
        binding.search.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        // 로그인시 회원정보창 수정

        // 퀴즈버튼 이벤트
        binding.quizBtn.setOnClickListener(v -> {
            int quizNumber = getRandomNumber(1, 100);
            quizRequest(quizNumber);
        });


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

        return binding.getRoot();
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
                    @Override
                    public void onResponse(String response) {
                        Log.d("quizdatacheck", response);
                        handleQuizLoad(response);
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
            Log.d("QuizDataCheck", quizContent + "/" + quizAnswer + "/" + quizInfo + "/" + quizPoint);
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putExtra("quizContent", quizContent);
            intent.putExtra("quizAnswer", quizAnswer);
            intent.putExtra("quizInfo", quizInfo);
            intent.putExtra("quizPoint", quizPoint);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}