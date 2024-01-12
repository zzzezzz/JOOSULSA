package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityPersonRankBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRankActivity extends AppCompatActivity {

    private ActivityPersonRankBinding binding;

    private SharedPreferences preferences;

    private RequestQueue requestQueue;

    private String personUrl = "http://192.168.219.62:8089/personRank";

    int postMethod = Request.Method.POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(PersonRankActivity.this);
        }

        personRankData();

        binding.btnPersonRankBack.setOnClickListener(v -> {
            finish();
        });


    }

    // 개인랭킹에 사용할 데이터 가져올거
    private void personRankData(){

        StringRequest request = new StringRequest(
                postMethod,
                personUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("personDataCheck", response);
                        personDataHandle(response);
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
                Map<String, String> param = new HashMap<>();
                return param;
            }
        };

        requestQueue.add(request);
    }

    private void personDataHandle(String response) {

        try {
            JSONArray jsonArray = new JSONArray(response);

            // User ID를 담을 리스트
            List<String> userNickList = new ArrayList<>();

            // Total Points를 담을 리스트
            List<Long> totalPointsList = new ArrayList<>();

            // JSON 배열을 순회하며 데이터 추출
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String userNick = userData.getString("userNick");
                Long totalPoints = userData.getLong("totalPoints");
                Log.d("asdcaxczxvqa", userNick);
                // 추출한 데이터를 리스트에 추가
                userNickList.add(userNick);
                totalPointsList.add(totalPoints);
            }

            // 이제 userIdList와 totalPointsList를 원하는 방식으로 활용할 수 있습니다.
            for (int i = 0; i < userNickList.size(); i++) {
                String userNick = userNickList.get(i);
                Long totalPoints = totalPointsList.get(i);
                String stringPoints = String.valueOf(totalPoints);
                Log.d("nghntyb", stringPoints);
                Log.d("vcvnfgbd", userNick);
                String tvNickId = "personNick" + (i+1);
                int nickResourceId = getResources().getIdentifier(tvNickId, "id", getPackageName());
                Log.d("dasqwdcaqws", Integer.toString(nickResourceId));

                String tvScoreId = "personScore" + (i+1);
                int scoreResourceId = getResources().getIdentifier(tvScoreId, "id", getPackageName());

                TextView nickTextView = findViewById(nickResourceId);
                Log.d("asmntrbfg", nickTextView.toString());
                TextView scoreTextView = findViewById(scoreResourceId);
                Log.d("asmntrbvx", scoreTextView.toString());

                nickTextView.setText(userNick.toString());
                scoreTextView.setText(stringPoints + " pt");

                // 원하는 로직 수행
                Log.d("UserRankData", "User ID: " + userNick + ", Total Points: " + totalPoints);
            }

            // 나머지 로직 계속 작성
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}