package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.joosulsa.databinding.ActivitySplashBinding;

import java.util.concurrent.CountDownLatch;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    private static final long MIN_SPLASH_TIME = 2000; // 최소 스플래시 시간 2초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handler 선언
        Handler handler = new Handler();



        // 일정 시간 후에 메인 액티비티로 이동(추후 추가 로직 추가 필요할 수 있음)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        }, MIN_SPLASH_TIME);


    }


    // MainActivity로의 이동
    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 스플래시 액티비티 종료
    }





}