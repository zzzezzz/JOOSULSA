package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        //회원가입 버튼 클릭시 회원가입 버튼으로 이동.
        binding.loginJoin.setOnClickListener(view->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        // 뒤로가기 버튼 클릭시 홈 화면으로 이동
        binding.loginBack.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        });
        // 홈 버튼 클릭시 홈 화면으로 이동
        binding.loginHoam.setOnClickListener(v->{
            Intent intent= new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}