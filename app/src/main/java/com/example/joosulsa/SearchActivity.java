package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivitySearchBinding;
import com.example.joosulsa.databinding.ActivitySearchDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    // Queue 쓸거
    private RequestQueue queue;
    // url 주소
    private String springUrl = "http://192.168.219.62:8089/search";

    private String viewRequestUrl = "http://192.168.219.62:8089/viewUp";
    // post
    int postMethod = Request.Method.POST;

    private RequestQueue requestQueue;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(SearchActivity.this);
        }

        // 버튼 이벤트 처리

        // 뒤로가기
        binding.searchBack.setOnClickListener(v -> {
            finish();
        });
        // 검색 버튼 클릭 시 이벤트
        binding.searchImg.setOnClickListener(v -> {

        });
        // 엔터 이벤트
        binding.textSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {

            if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // 검색 이벤트 처리
                Log.d("엔터", "된다");
                performSearch();
                return true; // 이벤트 처리 완료
            }
            return false; // 이벤트 처리하지 않음
        });

    }

    // 검색 기능 로직 처리
    private void performSearch() {
        // 여기에 실제 검색에 대한 로직을 추가하세요
        // 예를 들어, 검색어를 가져와서 검색 결과 화면으로 이동하는 등의 동작을 수행합니다.

        String search = binding.textSearch.getText().toString();
        searchRequest(search);
        Log.d("확인1", search);


        // 검색어를 이용한 추가 동작 수행

    }

    // 서버 통신
    private void searchRequest(String search) {
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                response -> {
                    // 서버통신 성공시
                    Log.d("searchCheck", response); // 로그

                    handSearch(response);

                    // 조회수 추가 + 사용자 포인트 추가 메소드 만들겠음
                    preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                    String autoId = preferences.getString("autoId", null);
                    if (response!=null){
                        //upViewsPoint(autoId);
                    }

                    // 키워드에 따른 페이지 이동 이벤트
                    if(response.equals("")){
                        // DB에 있는 키워드가 있을때.
                        Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                        startActivity(intent);
                    }else {
                        // DB에 없는 키워드 일때
                        Intent intent = new Intent(SearchActivity.this, RecycleDetailActivity.class);
                        startActivity(intent);
                    }

                },
                error -> {
                    // 서버통신 실패시
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("search",search);
                Log.d("가냐..?",search);
                return params;
            }
        };
        // Request를 RequestQueue에 추가
        requestQueue.add(request);
    }

    // Spring 에서 받아온 재활용 데이터 처리 메소드
    private void handSearch(String response) {
//                sepaMethod = 분리수거 방법
//                sepaCaution = 분리수거 주의사항
//                sepaImg = 분리수거 이미지
//                sepaVideo = 분리수거 영상
//                recycleVideo = 업사이클 비디오
//                recycleImg = 업사이클 이미지
        try {
            // 데이터 파싱 작업
            JSONObject jsonResponse = new JSONObject(response);
            String sepaMethod = jsonResponse.getString("sepaMethod");
            String sepaCaution = jsonResponse.getString("sepaCaution");
            String sepaImg = jsonResponse.getString("sepaImg");
            String sepaVideo = jsonResponse.getString("sepaVideo");
            String recycleVideo = jsonResponse.getString("recycleVideo");
            String recycleImg = jsonResponse.getString("recycleImg");
            // 확인
            Log.d("데이터 처리","방법: "+sepaMethod+" 주의사항: "+ sepaCaution +
                    " 이미지: "+ sepaImg + " 분리수거 영상: "+ sepaVideo+ " 업사이클 영상: "+ recycleVideo+
                    " 업사이클 이미지 : " + recycleImg);
            // Intent에 값 넣어주기
            Intent intent = new Intent(SearchActivity.this, RecycleDetailActivity.class);
            intent.putExtra("sepaMethod",sepaMethod);
            intent.putExtra("sepaCaution",sepaCaution);
            intent.putExtra("sepaImg",sepaImg);
            intent.putExtra("sepaVideo",sepaVideo);
            intent.putExtra("recycleVideo",recycleVideo);
            intent.putExtra("recycleImg",recycleImg);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 조회수 추가 + 포인트 추가 메소드
//    private void upViewsPoint(String autoId) {
//
//        StringRequest stringRequest = new StringRequest(
//                postMethod,
//                viewRequestUrl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }
//
//        )
//
//
//
//
//
//
//
//
//    }
}