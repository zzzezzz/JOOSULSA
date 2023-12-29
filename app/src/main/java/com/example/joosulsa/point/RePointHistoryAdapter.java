package com.example.joosulsa.point;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;

public class RePointHistoryAdapter extends RecyclerView.Adapter<RepointHistoryViewHolder> {


    // 정보가 들어갈 ArrayList 생성
    ArrayList<RePointHistoryVO> dataset;

    // ArrayList 초기화


    public RePointHistoryAdapter(ArrayList<RePointHistoryVO> dataset) {
        this.dataset = dataset;
    }

    // 레이아웃 뿌려주기
    @NonNull
    @Override
    public RepointHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_view,parent,false);

        RepointHistoryViewHolder holder = new RepointHistoryViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepointHistoryViewHolder holder, int position) {
        String titHistory = dataset.get(position).getTitle();
        String contentHistory = dataset.get(position).getContent();

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
