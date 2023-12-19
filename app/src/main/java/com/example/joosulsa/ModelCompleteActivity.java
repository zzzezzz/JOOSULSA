package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityModelCompleteBinding;

public class ModelCompleteActivity extends AppCompatActivity {
    private ActivityModelCompleteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModelCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}