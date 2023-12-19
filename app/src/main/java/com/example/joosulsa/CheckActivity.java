package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityCheckBinding;

public class CheckActivity extends AppCompatActivity {
    private ActivityCheckBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}