package com.example.joosulsa.shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;


public class ShopListViewHolder extends RecyclerView.ViewHolder {

    // 참조할 xml 요소 정의
    private ImageView img;
    private TextView proTitle, proInfo, proPrice;

    // 클릭 이벤트 사용하기 위해 객체 선언
    // ItemClickListener listener;

    // 생성자 생성
    public ShopListViewHolder(@NonNull View itemView) {super(itemView);

        this.img = itemView.findViewById(R.id.img);
        this.proTitle = itemView.findViewById(R.id.proTitle);
        this.proInfo = itemView.findViewById(R.id.proInfo);
        this.proPrice = itemView.findViewById(R.id.proPrice);

    }

    public ImageView getImg() {
        return img;
    }

    public TextView getProTitle() {
        return proTitle;
    }

    public TextView getProInfo() {
        return proInfo;
    }


    public TextView getProPrice() {
        return proPrice;
    }


}
