package com.example.joosulsa.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;

// extends해서 오류 뜰 경우 클릭해서 implements 메서드 만들어서 오류 해결
public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryViewHolder> {

    // 정보가 들어갈 Arraylist 생성
    ArrayList<MainCategoryVO> dataset;

    // 초기화 작업
    // Arraylist 초기화
    public MainCategoryAdapter(ArrayList<MainCategoryVO> dataset) {
        this.dataset = dataset;
    }


    // 레이아웃 뿌려주기
    @NonNull
    @Override
    public MainCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view,parent,true);

        MainCategoryViewHolder holder = new MainCategoryViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryViewHolder holder, int position) {
        // 데이터 초기화 해주는 작업
        holder.getIconCategory().setImageResource(dataset.get(position).getImg());
        holder.getTitCategory().setText(dataset.get(position).getTitle());

        // 클릭 이벤트 생성
        holder.listener = new MainCategoryClickListener() {
            @Override
            public void onMainCategoryClickListener(View v, int position) {

            }
        };

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

