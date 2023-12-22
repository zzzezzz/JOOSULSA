package com.example.joosulsa.purchase_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListViewHolder> {


    // 정보가 들어갈 Arraylist 생성
    ArrayList<PurchaseListVO> dataset;

    // 초기화 작업
    // Arraylist 초기화
    public PurchaseListAdapter(ArrayList<PurchaseListVO> dataset) {
        this.dataset = dataset;
    }

    // 레이아웃 뿌려주는 작업
    @NonNull
    @Override
    public PurchaseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item_view,parent,false);

        PurchaseListViewHolder holder = new PurchaseListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseListViewHolder holder, int position) {
        // 데이터 초기화 작업
        holder.getProductImg().setImageResource(dataset.get(position).getImg());
        holder.getPchTitle().setText(dataset.get(position).getTitle());
        holder.getPchContent().setText(dataset.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
