package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivitySearchDetailBinding;

public class SearchDetailActivity extends AppCompatActivity {
    private ActivitySearchDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivitySearchDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}