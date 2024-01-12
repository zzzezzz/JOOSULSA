package com.example.joosulsa;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityTownRankBinding;
import com.example.joosulsa.fragment.RankFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownRankActivity extends AppCompatActivity {

    private ActivityTownRankBinding binding;

    // 사용자가 속한 동네의 포인트를 가지고 오기 위해
    // 로그인 한 사용자 아이디를 가지고 오기 위해 사용
    private SharedPreferences preferences;

    // 서버에 요청 보내기 필요함
    private RequestQueue requestQueue;
    
    // 상위 동네 랭킹 데이터 뽑아주기 위한 링크
    private String townRankUrl = "http://192.168.219.62:8089/townList";
    
    // 로그인 한 사용자가 속한 동네의 포인트를 뽑아주기 위한 링크
    private String userTownPointUrl = "http://192.168.219.62:8089/userTownPoint";

    // 로그인 한 사용자가 속한 동네의 좌표값을 뽑아주기 위한 링크
    private String userTownGpsUrl = "http://192.168.219.62:8089/map";


    int postMethod = Request.Method.POST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 로그인 한 아이디 전역변수 선언
        // 스프링의 param값과 이름을 일치시켜주자. 안그러면 매개변수가 달라 오류난다.
        String userId = autoIdMethod(preferences);



        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(TownRankActivity.this);

        }




        // 홈으로
        binding.townHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TownRankActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        // 사용자가 속한 동네의 좌표값을 가지고 오는 메소드
        // 좌표값을 처리해주기 위해서 아이디 값을 보내주자.
        townListData(userId);

        // 밑에 정의한 메소드
        // 동네 상위랭킹 뽑아주는 메소드
        townRankData();

        // 사용자가 속한 동네의 포인트를 가지고 오는 메소드
        // 메소드 안에 로그인한 사용자 아이디를 넣어주자.
        userTownPointData(userId);


        // 웹 지도
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

            finish();
        });
        
    }

    
    // 사용자가 속한 동네의 좌표값 보내기
    private void townListData(String userId){

        StringRequest request = new StringRequest(
                postMethod,
                userTownGpsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("오냐고~~~~~", response);
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
                param.put("userId", userId); // 스프링으로 현재 로그인한 id값 전송
                Log.d("아이디 와줘", userId);
                return param;
            }
        };
        requestQueue.add(request);
    }


    // 동네 랭킹에 상위랭킹 3개만 뽑아올 때 사용할 데이터 가져오는 요청 및 응답 처리
    private void townRankData(){

        StringRequest request = new StringRequest(
                postMethod,
                townRankUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("오냐고~~~~~", response);
                        townRankDataHandle(response);
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

    // 동네 랭킹에 상위랭킹 3개만 뽑아올 때 사용할 데이터 가져오는 처리
    private void townRankDataHandle(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);

            // townName을 담을 리스트
            List<String> townNameList = new ArrayList<>();

            // Total Points를 담을 리스트
            List<Long> totalPointsList = new ArrayList<>();

            // JSON 배열을 순회하며 데이터 추출
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject townData = jsonArray.getJSONObject(i);
                String townName = townData.getString("townName");

                Log.d("데이터 확인", townName);
                Log.d("데이터 확인", townData.toString());


                Long totalPoints = townData.getLong("totalPoints");
                Log.d("테스트",  String.valueOf(jsonArray.getJSONObject(i)));
                // 추출한 데이터를 리스트에 추가
                townNameList.add(townName);
                totalPointsList.add(totalPoints);
            }

            // townNameList와 totalPointsList를 원하는 방식으로 활용
            for (int i = 0; i < townNameList.size()-1; i++) {
                String townName = townNameList.get(i);
                Long totalPoints = totalPointsList.get(i);
                String stringPoints = String.valueOf(totalPoints);
                Log.d("총합 포인트 내역", stringPoints);
                Log.d("동네 이름", townName);

                String tvTownName = "townRankNm" + (i+1);
                int tvTownNameId = getResources().getIdentifier(tvTownName, "id", getPackageName());
                Log.d("동네 이름 가지고 오나?", Integer.toString(tvTownNameId));

                String tvScoreId = "townRankSc" + (i+1);
                int scoreResourceId = getResources().getIdentifier(tvScoreId, "id", getPackageName());

                TextView townNameTextView = findViewById(tvTownNameId);
                if (townNameTextView != null) {
                    // townNameTextView가 정상적으로 초기화되었을 때의 로직
                    townNameTextView.setText(townName.toString());
                } else {
                    Log.e("TownRankActivity", "townNameTextView is null");
                }
                // Log.d("asmntrbfg", townNameTextView.toString());
                TextView scoreTextView = findViewById(scoreResourceId);
                // Log.d("asmntrbvx", scoreTextView.toString());

                if (scoreTextView != null) {
                    // scoreTextView가 정상적으로 초기화되었을 때의 로직
                    scoreTextView.setText(stringPoints + " pt");
                } else {
                    Log.e("TownRankActivity", "scoreTextView is null");
                }

                // 원하는 로직 수행
                Log.d("townRankData", "townName: " + townName + ", Total Points: " + totalPoints);
            }

            // 나머지 로직 계속 작성
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    // 사용자 user_id가 속한 동네의 포인트 총합을 가지고 오는 응답처리
    private void userTownPointData(String userId){

        StringRequest request = new StringRequest(
                postMethod,
                userTownPointUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("오냐고~~~~~", response);
                        // response : 스프링에서 보내준 응답 값
                        // 값을 하나만 가지고 오기 때문에 이렇게만 처리해주자.
                        // text는 String값이기 때문에 문자열로 바꿔준다.
                        binding.userTownPoint.setText(" "+response.toString()+" 포인트");
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
                param.put("userId", userId); // 스프링으로 현재 로그인한 id값 전송
                return param;
            }
        };

        requestQueue.add(request);
    }


    // 사용자 동네에 해당하는 포인트 점수를 가지고 오기 위해 선언
    // 로그인 한 사용자 아이디를 가지고 오는 메소드
    private String autoIdMethod(SharedPreferences preferences){
        Log.d("autoIdCheck", "Asdasd");
        preferences = TownRankActivity.this.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoId = preferences.getString("autoId", null);
        // Log.d("casdas", autoId);
        return autoId;
    }



}