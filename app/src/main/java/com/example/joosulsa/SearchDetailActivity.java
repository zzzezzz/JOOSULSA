package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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