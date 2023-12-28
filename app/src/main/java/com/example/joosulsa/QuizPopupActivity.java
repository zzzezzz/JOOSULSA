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
import com.example.joosulsa.databinding.ActivityQuizPopupBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuizPopupActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ActivityQuizPopupBinding binding;

    private RequestQueue requestQueue;

    int postMethod = Request.Method.POST;

    // 우리 스프링 주소 넣어둘 변수
    private String springUrl = "http://192.168.219.44:8089/quizPoint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoId = preferences.getString("autoId", null);

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(QuizPopupActivity.this);
        }

        Intent intent = getIntent();

        String quizInfo = intent.getStringExtra("quizInfo");
        int quizNum = intent.getIntExtra("quizNum", 0);
        String distinct = intent.getStringExtra("distinct");

        if (distinct.equals("wrong")){
            binding.titAnswer.setText("오답입니다");
            binding.commentary.setText(quizInfo);
        }else {
            binding.titAnswer.setText("정답입니다");
            binding.commentary.setText(quizInfo);
            quizPointRequest(quizNum, autoId);
        }

        // 홈으로 버튼 클릭 시 홈 Fragment로 이동
        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizPopupActivity.this, MainActivity.class);
                startActivity(intent);
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

    // 퀴즈 맞추면 포인트 추가 요청 보내는 메소드
    private void quizPointRequest(int quizNum, String autoId){
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("quiquiquiqui", response);
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
                params.put("correctQuizNum", Integer.toString(quizNum));
                params.put("correctUserId", autoId);
                long now =System.currentTimeMillis();
                Date today =new Date(now);
                SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = format.format(today);
                params.put("earnTime", time);
                return params;
            }
        };
        requestQueue.add(request);
    }

}
