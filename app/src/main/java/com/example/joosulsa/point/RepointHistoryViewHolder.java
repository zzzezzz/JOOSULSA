package com.example.joosulsa.point;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

public class RepointHistoryViewHolder extends RecyclerView.ViewHolder {

    // 참조할 xml 요소 정의
    private TextView titHistory, contentHistory;

    public RepointHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        this.titHistory = itemView.findViewById(R.id.titHistory);
        this.contentHistory = itemView.findViewById(R.id.contentHistory);

    }

    public TextView getTitHistory() {
        return titHistory;
    }

    public TextView getContentHistory() {
        return contentHistory;
    }


}
