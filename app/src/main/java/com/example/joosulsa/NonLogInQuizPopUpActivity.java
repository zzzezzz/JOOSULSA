package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityNonLogInQuizPopUpBinding;

public class NonLogInQuizPopUpActivity extends AppCompatActivity {

    private ActivityNonLogInQuizPopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNonLogInQuizPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nonLogMain.setOnClickListener(v -> {
            Intent intent = new Intent(NonLogInQuizPopUpActivity.this, MainActivity.class);

            startActivity(intent);

            finish();
        });

        binding.nonLogLog.setOnClickListener(v -> {

            Intent intent = new Intent(NonLogInQuizPopUpActivity.this, LoginActivity.class);

            startActivity(intent);

            finish();

        });






    }
}