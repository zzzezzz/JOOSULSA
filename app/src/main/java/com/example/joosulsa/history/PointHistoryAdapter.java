package com.example.joosulsa.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;

public class PointHistoryAdapter extends RecyclerView.Adapter<PointHistoryViewHolder> {

    // 정보가 들어갈 Arraylist생성
    ArrayList<PointHistoryVO> dataset;

    // 초기화 작업
    // Arraylist 초기화
    public PointHistoryAdapter(ArrayList<PointHistoryVO> dataset) {
        this.dataset = dataset;
    }

    // 레이아웃 뿌려주기
    @NonNull
    @Override
    public PointHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_view,parent,false);

        PointHistoryViewHolder holder = new PointHistoryViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointHistoryViewHolder holder, int position) {
        // 데이터 초기화 작업
        holder.getTitPointHistory().setText(dataset.get(position).getTitle());
        holder.getContentPointHistory().setText(dataset.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
