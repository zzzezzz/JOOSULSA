package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.StringRequest;
import com.example.joosulsa.databinding.ActivitySearchDetailBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchDetailActivity extends AppCompatActivity {
    private ActivitySearchDetailBinding binding;
    private String springUrl = "http://192.168.219.62:8089/search";

    // post
    int postMethod = Request.Method.POST;

    private RequestQueue requestQueue;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivitySearchDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // 뒤로가기
        binding.backSearchDetail.setOnClickListener(v -> {
            Intent intent = new Intent(SearchDetailActivity.this, SearchActivity.class);
            startActivity(intent);
        });
        // 엔터 이벤트 설정
        binding.textSearchDetail.setOnEditorActionListener((v, actionId, enter) -> {
            if(actionId==EditorInfo.IME_ACTION_SEARCH || (enter !=null && enter.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                // IME_ACTION_SEARCH: 사용자가 키보드에서 "검색" 액션을 수행했을 때 또는 Enter 키를 눌렀을 때
                Log.d("엔터 확인", "된다");
                searchDetail();
                return true; // 이벤트 처리 완료
            }
            return false; // 이벤트 처리하지 않음
        });
    }

    // 검색 기능 로직 처리
    private void searchDetail(){
        String search = binding.textSearchDetail.getText().toString();
        // 검색 방법
        String searchMethod = "text";
        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        String autoId = preferences.getString("autoId", null);
        searchRequest(search);
        Log.d("확인123", search);
    }
    // 서버통신
    private void searchRequest(String search){
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                response -> {
                    Log.d("검색 통신 성공",response);
                },
                error -> {
                    Log.d("검색 통신 실패","??");
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("search",search);
                Log.d("갔니?",search);
                return params;
            }
        };
        requestQueue.add(request);
    }



}