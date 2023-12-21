package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private RequestQueue requestQueue;

    // 우리 스프링 주소 넣어둘 변수
    private String springUrl = "나중에 스프링 켜지면 여기다 스프링 주소 넣으면 됨";

    // 회원가입이라 post로 했는데 아니면 바꾸죠 뭐
    int getMethod = Request.Method.POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // join_back(뒤로가기) 눌렀을대 메인으로 이동하기


        // 회원가입시 입력 정보 보내는 로직 만들겠음

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }

        // 회원가입 버튼 클릭 이벤트
        binding.btnJoin.setOnClickListener(v -> {
            String userName = binding.joinName.getText().toString();
            String userID = binding.joinId.getText().toString();


        });


    }

    // 회원가입 데이터 보내는 메소드
    private void registerRequest(){

    }
}