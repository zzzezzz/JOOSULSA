package com.example.joosulsa.history;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

public class PointHistoryViewHolder extends RecyclerView.ViewHolder {

   // history_item_view의 아이템 id
   private TextView titPointHistory, contentPointHistory;

   // 초기화
    public PointHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.titPointHistory = itemView.findViewById(R.id.titPointHistory);
        this.contentPointHistory = itemView.findViewById(R.id.contentPointHistory);

    }

    // getter
    public TextView getTitPointHistory() {
        return titPointHistory;
    }
    public TextView getContentPointHistory() {
        return contentPointHistory;
    }

}
