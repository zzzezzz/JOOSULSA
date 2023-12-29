package com.example.joosulsa.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListViewHolder> {

   // 정보가 들어갈 ArrayList 생성
    ArrayList<ShopListVO> dataset;

    // ArrayList 초기화
    public ShopListAdapter(ArrayList<ShopListVO> dataset) {
        this.dataset = dataset;
    }

    // 레이아웃 뿌려주기
    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_view,parent,false);

        ShopListViewHolder holder = new ShopListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder holder, int position) {
        int img = dataset.get(position).getImg();
        int img2 = dataset.get(position).getImg2();
        String proTitle = dataset.get(position).getTitle();
        String proTitle2 = dataset.get(position).getTitle2();
        String proInfo = dataset.get(position).getContent();
        String proInfo2 = dataset.get(position).getContent2();
        int proPrice = dataset.get(position).getPrice();
        int proPrice2 = dataset.get(position).getPrice2();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
