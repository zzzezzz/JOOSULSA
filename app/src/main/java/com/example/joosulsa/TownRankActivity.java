package com.example.joosulsa;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    // 서버에 요청 보내기 필요함
    private RequestQueue requestQueue;

    private String townRankUrl = "http://192.168.219.42:8089/townList";

    int postMethod = Request.Method.POST;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(TownRankActivity.this);

        }

        // 뒤로 가기
        binding.btnTownRankBack.setOnClickListener(v -> {
            Intent intent = new Intent(TownRankActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        // 홈으로
        binding.townHomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TownRankActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });



        // 밑에 정의한 메소드
        townRankData();




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

        // 뒤로 가기 버튼

    }



    // 동네 랭킹에 사용할 데이터 가져올거
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
            for (int i = 0; i < townNameList.size(); i++) {
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
                Log.d("asmntrbfg", townNameTextView.toString());
                TextView scoreTextView = findViewById(scoreResourceId);
                Log.d("asmntrbvx", scoreTextView.toString());

                townNameTextView.setText(townName.toString());
                scoreTextView.setText(stringPoints+" pt");

                // 원하는 로직 수행
                Log.d("townRankData", "townName: " + townName + ", Total Points: " + totalPoints);
            }

            // 나머지 로직 계속 작성
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}