package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityMyPageInfoBinding;
import com.example.joosulsa.databinding.ActivityPersonRankBinding;

public class MyPageInfoActivity extends AppCompatActivity {
    private ActivityMyPageInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPageInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}