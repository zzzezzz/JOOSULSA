package com.example.joosulsa.purchase_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

public class PurchaseListViewHolder extends RecyclerView.ViewHolder {

    // item_view.xml의 id
    private ImageView productImg;
    private TextView pchTitle, pchContent;


    public PurchaseListViewHolder(@NonNull View itemView) {
        super(itemView);
        // 초기화 작업
        this.productImg = itemView.findViewById(R.id.productImg);
        this.pchTitle = itemView.findViewById(R.id.pchTitle);
        this.pchContent = itemView.findViewById(R.id.pchContent);

    }



    // getter메소드 생성
    public ImageView getProductImg() {
        return productImg;
    }

    public TextView getPchTitle() {
        return pchTitle;
    }

    public TextView getPchContent() {
        return pchContent;
    }



}
