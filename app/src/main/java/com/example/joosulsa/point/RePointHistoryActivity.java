package com.example.joosulsa.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.joosulsa.MainActivity;
import com.example.joosulsa.databinding.ActivityRePointHistroyBinding;

import java.util.ArrayList;

public class RePointHistoryActivity extends AppCompatActivity {

    private ArrayList<RePointHistoryVO> dataset;
    private RePointHistoryAdapter adapter;
    private ActivityRePointHistroyBinding binding;

    private SharedPreferences preferences;

    private String poUrl ="http://192.168.219.62:8089/poHistroy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRePointHistroyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataset = new ArrayList<>();

        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String autoNick = preferences.getString("autoNick", "000");
        int totalPoint = preferences.getInt("totalPoints", 0);
        String totalP = String.valueOf(totalPoint);

        binding.pointUserNick.setText(autoNick + "님의 포인트 지갑");
        binding.pointTotalP.setText(totalP);

        dataset.add(new RePointHistoryVO("test","test"));
        dataset.add(new RePointHistoryVO("test","test"));
        dataset.add(new RePointHistoryVO("test","test"));
        dataset.add(new RePointHistoryVO("test","test"));
        dataset.add(new RePointHistoryVO("test","test"));

        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // 레이아웃 설정
        binding.rePointHistoryList.setLayoutManager(manager);

        // 어댑터 연결
        adapter = new RePointHistoryAdapter(dataset);
        binding.rePointHistoryList.setAdapter(adapter);

        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RePointHistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}