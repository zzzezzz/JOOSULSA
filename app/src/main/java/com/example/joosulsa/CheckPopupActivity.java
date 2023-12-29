package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.joosulsa.databinding.ActivityCheckPopupBinding;

public class CheckPopupActivity extends AppCompatActivity {

    private ActivityCheckPopupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 팝업창 종료
        binding.checkPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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