package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joosulsa.databinding.ActivityMyPageEditBinding;

public class MyPageEditActivity extends AppCompatActivity {

    private ActivityMyPageEditBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPageEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 수정하기 버튼 클릭 이벤트
        binding.btnMyPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnMyPw.setVisibility(View.GONE);
                // 수정하기 버튼 안보이게 비활성화
                binding.btnMyPw2.setVisibility(View.VISIBLE);
                // 숨김 버튼 보이게 활성화
                binding.pwChange.setVisibility(View.VISIBLE);
                // 수정하는 레이아웃 활성화
            }
        });
        // 숨김 버튼 클릭 이벤트
        binding.btnMyPw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnMyPw.setVisibility(View.VISIBLE);
                // 수정하기 버튼 보이게 활성화
                binding.btnMyPw2.setVisibility(View.GONE);
                // 숨김 버튼 안보이게 비활성화
                binding.pwChange.setVisibility(View.GONE);
                // 수정하는 레이아웃 비활성화
            }
        });


    }
}