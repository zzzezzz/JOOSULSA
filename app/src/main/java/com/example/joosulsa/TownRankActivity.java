package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.example.joosulsa.databinding.ActivityTownRankBinding;

public class TownRankActivity extends AppCompatActivity {

    private ActivityTownRankBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. url 값 적어줄 때 카카오 api 연결할 도메인 값도 일치 시킬 것
        // 2. 로컬 아이피이기 때문에 같은 인터넷을 사용해야해서 연결 시 오류가 날 경우 단말의 인터넷 설정을 확인해보자.(와이파이가 연결되어 있지 않다면 인터넷이 뜨지 않음)
        String url = "http://192.168.219.42:8089/map"; // defValue : 오류가 나거나 안뜰때 작성해주는 곳

        // 2. WebView를 설정 할 수 있는 객체
        // 3. WebView의 js 사용을 true로 변경
        binding.WebView.getSettings().setJavaScriptEnabled(true);

        // 3. WebView의 클라이언트
        binding.WebView.setWebViewClient(new WebViewClient()); // 내가 갖고 있는 앱으로 띄워주려면 ..

        // 4. url 적용
        binding.WebView.loadUrl(url);

        binding.btnTownRankBack.setOnClickListener(v -> {
            Intent intent = new Intent(TownRankActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}