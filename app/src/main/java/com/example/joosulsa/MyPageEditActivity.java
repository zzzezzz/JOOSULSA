package com.example.joosulsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityMyPageEditBinding;

public class MyPageEditActivity extends AppCompatActivity {

    private ActivityMyPageEditBinding binding;

    private SharedPreferences spf;

    private RequestQueue queue;

    // 현재 비밀번호 확인여부 메소드
    private void checkPasswordMatch() {
        String currentPassword = binding.myPw.getText().toString();
        String enteredPassword = binding.myPwCheck.getText().toString();

        if (currentPassword.equals(enteredPassword)) {
            binding.pwNm.setVisibility(View.GONE); // 텍스트 숨김
            binding.myChangeOK.setVisibility(View.VISIBLE); // 완료버튼 출력
            binding.myChangeNo.setVisibility(View.GONE);    // 비활성화 숨김
        } else {
            binding.pwNm.setVisibility(View.VISIBLE); // 텍스트 출력
            binding.myChangeOK.setVisibility(View.GONE); // 완료버튼 숨김
            binding.myChangeNo.setVisibility(View.VISIBLE);// 비활성화 출력
        }
    }

    // 새로운 비밀번호 일치 여부 확인 메소드
    private void newPwCheck(){
        String newpw = binding.newPw.getText().toString();
        String newpwcheck = binding.newPwCheck.getText().toString();

        if(newpw.equals(newpwcheck)){
            binding.newPwNm.setVisibility(View.GONE); // 텍스트 숨김
            binding.myChangeOK.setVisibility(View.VISIBLE); // 완료버튼 출력
            binding.myChangeNo.setVisibility(View.GONE);    // 비활성화 숨김
        }else{
            binding.newPwNm.setVisibility(View.VISIBLE); // 텍스트 출력
            binding.myChangeOK.setVisibility(View.GONE); // 완료버튼 숨김
            binding.myChangeNo.setVisibility(View.VISIBLE);// 비활성화 출력
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPageEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 페이지 이동

        // 뒤로가기 이벤트
        binding.myBack.setOnClickListener(v -> {
            finish();
        });
        // 변경완료 이벤트
        binding.myChangeOK.setOnClickListener(v -> {
            finish();
        });

        // 데이터 가져오기
        spf = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String myId = spf.getString("autoId", null);
        String myPw = spf.getString("autoPw", null);
        String myName = spf.getString("autoName", null);
        String myAddr = spf.getString("autoAddr", null);
        String myNick = spf.getString("autoNick", null);

        binding.myId.setText(myId);
        binding.myPw.setText(myPw);
        binding.myName.setText(myName);
        binding.myNick.setText(myNick);
        binding.myAddress.setText(myAddr);


        //현재 비밀번호 일치여부 확인하기
        binding.myPwCheck.addTextChangedListener(new TextWatcher() {
            // int start = 시작전
            // int count = 과정
            // int after = 결과
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 변경 시작점, 변경 과정, 변경 결과
                // 텍스트가 변경되기 전 이벤트
                binding.pwNm.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 변경 시작점, 변경 과정, 변경 결과
                // 텍스트 변화가 있을 경우
                checkPasswordMatch(); // 현재 비밀번호 일치 여부 메소드 호출
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트 변경후 이벤트
            }
        });

        // 새로운 비밀번호 일치 여부 확인
        binding.newPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newPwCheck();
            }
        });


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

                // 현재 비밀번호 체크 메소드
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
        //
    }
}