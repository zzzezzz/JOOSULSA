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
        Intent intent = getIntent();
        String quizContent = intent.getStringExtra("quizContent");
        String quizAnswer = intent.getStringExtra("quizAnswer");
        String quizInfo = intent.getStringExtra("quizInfo");
        int quizPoint = intent.getIntExtra("quizPoint", 100);
        int quizNum = intent.getIntExtra("quizNum", 0);
        binding.titQuiz.setText(quizContent);

        String correct = "O";
        String wrong = "X";

        // O 버튼을 클릭 시 팝업창 뜨는 코드
        binding.correctBtn.setOnClickListener(v -> {
            if(quizAnswer.equals(correct)){
                Intent intent1 = new Intent();
                intent1.putExtra("quizPoint", quizPoint);
                intent1.putExtra("quizNum", quizNum);
            }else {
                Intent intent2 = new Intent();
                intent2.putExtra("quizInfo", quizInfo);
            }
        });

        // X 버튼을 클릭 시 팝업창 뜨는 코드
        binding.errorBtn.setOnClickListener(v -> {
            if (quizAnswer.equals(wrong)){
                Intent intent3 = new Intent();
                intent3.putExtra("quizPoint", quizPoint);
                intent3.putExtra("quizNum", quizNum);
            }else {
                Intent intent4 = new Intent();
                intent4.putExtra("quizInfo", quizInfo);
            }
        });
    }

}