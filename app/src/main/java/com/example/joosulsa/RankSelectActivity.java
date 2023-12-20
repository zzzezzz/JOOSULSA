package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityRankSelectBinding;

public class RankSelectActivity extends AppCompatActivity {
    private ActivityRankSelectBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRankSelectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}