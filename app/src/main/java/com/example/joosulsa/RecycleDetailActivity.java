package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityRecycleDetailBinding;

public class RecycleDetailActivity extends AppCompatActivity {
    private ActivityRecycleDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecycleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}