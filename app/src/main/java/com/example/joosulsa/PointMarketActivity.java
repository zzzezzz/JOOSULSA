package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityPointMarketBinding;

public class PointMarketActivity extends AppCompatActivity {
    private ActivityPointMarketBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPointMarketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}