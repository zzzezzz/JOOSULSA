package com.example.joosulsa.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

public class MainCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // 참조할 xml 요소 정의하기

    private CardView categoryBtn;

    private ImageView iconCategory;

    private TextView titCategory;

    // 클릭이벤트를 사용하기 위해 객체 선언
    MainCategoryClickListener listener;


    public MainCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        // 초기화 작업
        this.categoryBtn = itemView.findViewById(R.id.categoryBtn);
        this.iconCategory = itemView.findViewById(R.id.iconCategory);
        this.titCategory = itemView.findViewById(R.id.titCategory);

        // 버튼 눌렀을 때 동작
        categoryBtn.setOnClickListener(this); // this 사용하기 위해서 인터페이스에서 설정해줄 것
        
    }

    public CardView getCategoryBtn() {
        return categoryBtn;
    }

    public ImageView getIconCategory() {
        return iconCategory;
    }

    public TextView getTitCategory() {
        return titCategory;
    }

    // 추상메소드 작성 + 자동완성으로 오버라이드
    @Override
    public void onClick(View v) {
        this.listener.onMainCategoryClickListener(v,getLayoutPosition()); // 연결 작업
    }
}
