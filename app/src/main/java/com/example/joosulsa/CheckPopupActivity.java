package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityCheckPopupBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckPopupActivity extends AppCompatActivity {

    private ActivityCheckPopupBinding binding;

    private SharedPreferences preferences;

    private RequestQueue requestQueue;

    int postMethod = Request.Method.POST;

    private String springUrl = "http://192.168.219.62:8089/checkPoint";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int monthlyAtt = monthlyAtt(preferences);
        String autoId = preferences.getString("autoId", null);
        binding.checkNum.setText(Integer.toString(monthlyAtt));

        Log.d("checkData", monthlyAtt + autoId);
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(CheckPopupActivity.this);
        }

        // 팝업창 종료
        binding.checkPopupBtn.setOnClickListener(v -> {
            if (monthlyAtt<=28){
                checkPointRequest(autoId, monthlyAtt);
                plusMonthlyAtt(monthlyAtt);
                finish();
            } else if (monthlyAtt>28) {
                plusMonthlyAtt(monthlyAtt);
                finish();
            }
        });
    }

    // 팝업 밖 선택 시 닫힘 방지
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    // 출석 횟수 추가
    private int monthlyAtt(SharedPreferences preferences){
        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int monthlyAtt = preferences.getInt("monthlyAttendance", 0) + 1;
        return monthlyAtt;
    }

    // 출첵하면 포인트 추가, 출석여부 변경
    private void checkPointRequest(String autoId, int monthlyAtt){
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("checkResult", response);
                        handlePointResponse(response);
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
                params.put("autoId", autoId);
                params.put("monthlyAtt", Integer.toString(monthlyAtt));
                long now =System.currentTimeMillis();
                Date today =new Date(now);
                SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = format.format(today);
                params.put("earnTime", time);
                Log.d("checkParam", params.toString());
                return params;
            }
        };
        requestQueue.add(request);

    }

    // 출석 횟수 저장
    private void plusMonthlyAtt(int monthlyAtt){
        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("monthlyAttendance", monthlyAtt);
        editor.apply();
    }

    private void handlePointResponse(String response){

        try {
            JSONObject jsonResponse = new JSONObject(response);

            int userPoint = jsonResponse.getInt("totalPoints");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("userPoint", userPoint);
            editor.apply();
            Intent intent = new Intent(CheckPopupActivity.this, MainActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}