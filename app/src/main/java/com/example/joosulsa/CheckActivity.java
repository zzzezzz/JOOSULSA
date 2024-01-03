package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityCheckBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckActivity extends AppCompatActivity {
    private ActivityCheckBinding binding;

    private SharedPreferences preferences;

    private RequestQueue requestQueue;

    int postMethod = Request.Method.POST;

    private String springUrl = "http://192.168.219.62:8089/checkOutput";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        String autoId = preferences.getString("autoId", null);

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(CheckActivity.this);
        }

        // 출석횟수 정보 가져오고 글자 설정하는 메소드
        checkOutput(autoId);

        // 뒤로가기 버튼
        binding.btnCheckBack.setOnClickListener(v -> {
            Intent intent = new Intent(CheckActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    // 출석횟수 정보 가져오고 글자 설정하는 메소드
    private void checkOutput(String autoId){

        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        checkOutputHandle(response);
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

    private void checkOutputHandle(String response){
        try {
            JSONObject jsonResponse = new JSONObject(response);
            preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
            int monthlyAttNum = Integer.parseInt(jsonResponse.getString("monthlyAttendance"));
            Log.d("asdasdasd", Integer.toString(monthlyAttNum));
            binding.monthlyCheckNum.setText(Integer.toString(monthlyAttNum));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}