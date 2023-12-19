package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityPhotoSplashBinding;

public class PhotoSplashActivity extends AppCompatActivity {
    private ActivityPhotoSplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoSplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}