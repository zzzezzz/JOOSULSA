package com.example.joosulsa.point;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.joosulsa.MainActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.ActivityRePointHistroyBinding;

import java.util.ArrayList;

public class RePointHistroyActivity extends AppCompatActivity {

    private ArrayList<RePointHistoryVO> dataset;
    private RePointHistoryAdapter adapter;
    private ActivityRePointHistroyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRePointHistroyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataset = new ArrayList<>();

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
                Intent intent = new Intent(RePointHistroyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}