package com.example.joosulsa.category;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R; // 이 부분은 프로젝트에 맞게 수정해주세요.
import com.example.joosulsa.RecycleDetailActivity;

import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryViewHolder> {

    private List<MainCategoryVO> dataset;

    public MainCategoryAdapter(List<MainCategoryVO> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MainCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false);
        return new MainCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryViewHolder holder, int position) {
        String title = dataset.get(position).getTitle();
        int img = dataset.get(position).getImg();

        holder.getTitCategory().setText(title);
        holder.getIconCategory().setImageResource(img);

        holder.listener = new MainCategoryClickListener() {
            @Override
            public void onMainCategoryClickListener(View v, int position) {
                Intent intent = new Intent(v.getContext(), RecycleDetailActivity.class);

                MainCategoryVO clickedItem = dataset.get(position);
                String clickedTitle = clickedItem.getTitle();

                categoryRecySend(clickedTitle);

                v.getContext().startActivity(intent);
            }
        };
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }


    // 클릭한 카테고리 이름 받아와서 스프링에 조회하고 데이터 전달
    private void categoryRecySend(String clickedTitle){

    }

}
