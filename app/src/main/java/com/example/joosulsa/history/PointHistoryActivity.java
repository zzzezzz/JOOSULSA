package com.example.joosulsa.history;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.joosulsa.R;
import com.example.joosulsa.databinding.ActivityPointHistoryBinding;

import java.util.ArrayList;

public class PointHistoryActivity extends AppCompatActivity {


    private ArrayList<PointHistoryVO> dataset;

    private PointHistoryAdapter adapter;
    private ActivityPointHistoryBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPointHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataset = new ArrayList<>();

        dataset.add(new PointHistoryVO("test","test"));
        dataset.add(new PointHistoryVO("test","test"));
        dataset.add(new PointHistoryVO("test","test"));
        dataset.add(new PointHistoryVO("test","test"));

        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // 레이아웃 설정해주기
        binding.historyList.setLayoutManager(manager);

        // 어댑터 연결
        adapter = new PointHistoryAdapter(dataset);
        binding.historyList.setAdapter(adapter);


    }
}
