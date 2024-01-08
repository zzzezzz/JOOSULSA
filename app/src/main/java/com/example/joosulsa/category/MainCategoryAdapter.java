package com.example.joosulsa.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R; // 이 부분은 프로젝트에 맞게 수정해주세요.

import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MainCategoryViewHolder> {

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
        MainCategoryVO item = dataset.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class MainCategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleTextView;

        public MainCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // View에서 ImageView와 TextView를 찾음
            imageView = itemView.findViewById(R.id.iconCategory);
            titleTextView = itemView.findViewById(R.id.titCategory);
        }

        public void bind(MainCategoryVO item) {
            // Null 체크를 추가하여 안전하게 사용
            if (imageView != null) {
                imageView.setImageResource(item.getImg());
            }
            if (titleTextView != null) {
                titleTextView.setText(item.getTitle());
            }
        }
    }
}
