package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityPersonRankBinding;

public class PersonRankActivity extends AppCompatActivity {
    private ActivityPersonRankBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇ
    }
}