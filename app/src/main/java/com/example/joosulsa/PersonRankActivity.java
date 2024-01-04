package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityPersonRankBinding;

import java.util.HashMap;
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


    }

    // 개인랭킹에 사용할 데이터 가져올거
    private void personRankData(){

        StringRequest request = new StringRequest(
                postMethod,
                personUrl,
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
                Map<String, String> param = new HashMap<>();
                return param;
            }
        };

        requestQueue.add(request);
    }




}