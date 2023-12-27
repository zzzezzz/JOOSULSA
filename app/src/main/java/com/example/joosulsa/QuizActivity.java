package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.joosulsa.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent();
        String quizContent = intent.getStringExtra("quizContent");
        binding.titQuiz.setText(intent.getStringExtra("quizContent"));


        // 퀴즈 버튼을 클릭 시 팝업창 뜨는 코드
        binding.correctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(QuizActivity.this,QuizPopupActivity.class);
                    startActivity(intent);
            }
        });

    }






}