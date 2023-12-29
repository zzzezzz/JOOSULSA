package com.example.joosulsa.shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;


public class ShopListViewHolder extends RecyclerView.ViewHolder {

    // 참조할 xml 요소 정의
    private ImageView img, img2;
    private TextView proTitle, proTitle2, proInfo,proInfo2, proPrice, proPrice2;

    // 클릭 이벤트 사용하기 위해 객체 선언
    // ItemClickListener listener;

    // 생성자 생성
    public ShopListViewHolder(@NonNull View itemView) {
        super(itemView);

        this.img = itemView.findViewById(R.id.img);
        this.img2 = itemView.findViewById(R.id.img2);
        this.proTitle = itemView.findViewById(R.id.proTitle);
        this.proTitle2 = itemView.findViewById(R.id.proTitle2);
        this.proInfo = itemView.findViewById(R.id.proInfo);
        this.proInfo2 = itemView.findViewById(R.id.proInfo2);
        this.proPrice = itemView.findViewById(R.id.proPrice);
        this.proPrice2 = itemView.findViewById(R.id.proPrice2);

    }

    public ImageView getImg() {
        return img;
    }

    public ImageView getImg2() {
        return img2;
    }

    public TextView getProTitle() {
        return proTitle;
    }

    public TextView getProTitle2() {
        return proTitle2;
    }

    public TextView getProInfo() {
        return proInfo;
    }

    public TextView getProInfo2() {
        return proInfo2;
    }

    public TextView getProPrice() {
        return proPrice;
    }

    public TextView getProPrice2() {
        return proPrice2;
    }

}
