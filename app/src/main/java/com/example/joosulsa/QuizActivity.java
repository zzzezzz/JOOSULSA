package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.joosulsa.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    String distinct = null;

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
        Log.d("quizquizquizquiz", quizAnswer);
        binding.titQuiz.setText(quizContent);

        String correct = "O";
        String wrong = "X";


        // O 버튼을 클릭 시 팝업창 뜨는 코드
        binding.correctBtn.setOnClickListener(v -> {
            if(quizAnswer.equals(correct)){
                Intent intent1 = new Intent(getApplicationContext(),QuizPopupActivity.class);
                intent1.putExtra("quizPoint", quizPoint);
                intent1.putExtra("quizInfo", quizInfo);
                intent1.putExtra("quizNum", quizNum);
                distinct = "right";
                intent1.putExtra("distinct", distinct);
                startActivity(intent1);
            }else {
                Intent intent2 = new Intent(getApplicationContext(),QuizPopupActivity.class);
                intent2.putExtra("quizInfo", quizInfo);
                distinct = "wrong";
                intent2.putExtra("distinct", distinct);
                startActivity(intent2);
            }
        });

        // X 버튼을 클릭 시 팝업창 뜨는 코드
        binding.errorBtn.setOnClickListener(v -> {
            if (quizAnswer.equals(wrong)){
                Intent intent3 = new Intent(getApplicationContext(),QuizPopupActivity.class);
                intent3.putExtra("quizPoint", quizPoint);
                intent3.putExtra("quizInfo", quizInfo);
                intent3.putExtra("quizNum", quizNum);
                distinct = "right";
                intent3.putExtra("distinct", distinct);
                startActivity(intent3);
            }else {
                Intent intent4 = new Intent(getApplicationContext(),QuizPopupActivity.class);
                intent4.putExtra("quizInfo", quizInfo);
                distinct = "wrong";
                intent4.putExtra("distinct", distinct);
                startActivity(intent4);
            }
        });
    }

}