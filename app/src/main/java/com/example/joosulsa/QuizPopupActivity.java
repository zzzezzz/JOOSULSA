package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.joosulsa.databinding.ActivityQuizPopupBinding;
import com.example.joosulsa.fragment.HomeFragment;

public class QuizPopupActivity extends AppCompatActivity {

    private ActivityQuizPopupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 홈으로 버튼 클릭 시 홈 Fragment로 이동
        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizPopupActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        }

        // 팝업 밖 선택 시 닫힘 방지
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return false;
            }
            return true;
        }



}