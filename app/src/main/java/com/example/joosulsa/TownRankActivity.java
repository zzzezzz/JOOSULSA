package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityTownRankBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TownRankActivity extends AppCompatActivity {

    private ActivityTownRankBinding binding;

    // 서버에 요청 보내기 필요함
    private RequestQueue queue;

    private String springUrl = "http://172.30.48.1:8089/townList";

    int postMethod = Request.Method.POST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. url 값 적어줄 때 카카오 api 연결할 도메인 값도 일치 시킬 것
        // 2. 로컬 아이피이기 때문에 같은 인터넷을 사용해야해서 연결 시 오류가 날 경우 단말의 인터넷 설정을 확인해보자.(와이파이가 연결되어 있지 않다면 인터넷이 뜨지 않음)
        String url = "http://192.168.219.42:8089/map"; // defValue : 오류가 나거나 안뜰때 작성해주는 곳

        // 2. WebView를 설정 할 수 있는 객체
        // 3. WebView의 js 사용을 true로 변경
        binding.WebView.getSettings().setJavaScriptEnabled(true);

        // 3. WebView의 클라이언트
        binding.WebView.setWebViewClient(new WebViewClient()); // 내가 갖고 있는 앱으로 띄워주려면 ..

        // 4. url 적용
        binding.WebView.loadUrl(url);

        binding.btnTownRankBack.setOnClickListener(v -> {
            Intent intent = new Intent(TownRankActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

//
//        if (queue == null) {
//            queue = Volley.newRequestQueue(this);
//        }
//
//        String townName = binding.townRank1Nm.toString();
//        String townPoint = binding.townRank1Score.toString();
//
//        // 서버에 데이터 요청
//        StringRequest request = new StringRequest(
//                postMethod,
//                springUrl, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        handleLoginResponse(response);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("에러","서버에서 값을 받아오지 못했습니다.");
//                    }
//                }
//
//        ){
//            @Nullable
//            @Override
//            // Map<String(key), String(value)>를
//            // getParams를 사용해서 key와 value를 login?key=value 형태로 바꿔준다.
//            protected Map<String, String> getParams() throws AuthFailureError {
//                // 전송방식을 POST로 지정했을 때 사용하는 메소드
//                // 데이터를 전송할 때 Map 형태로 구성하여 리턴해줘야 한다.
//                Map<String, String> params = new HashMap<>();
//                params.put("townName", townName);
//                params.put("townPoint", townPoint);
//                Log.d("LoginParams", params.toString());
//                return params;
//            }
//        };
//        queue.add(request);
//
//    }

//
//    private void handleLoginResponse(String response) {
//        // 서버에서 받아온 응답을 처리하는 로직을 작성 : 스프링이 보내준 값을 안드로이드에서 받아주는 작업
//        // response는 서버에서 보낸 JSON 형태의 데이터일 것이므로, 필요에 따라 파싱하여 사용
//        try {
//            JSONObject jsonResponse = new JSONObject(response);
//            // json 파싱하는 부분
//            String townName = jsonResponse.getString("townName");
//            String townPoint = jsonResponse.getString("townPoint");
//
//
//            binding.townRank1Nm.setText(townName);
//            binding.townRank1Score.setText(townPoint);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    }
}