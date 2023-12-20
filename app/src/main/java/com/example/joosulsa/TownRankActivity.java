package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityTownRankBinding;

public class TownRankActivity extends AppCompatActivity {
    private ActivityTownRankBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}