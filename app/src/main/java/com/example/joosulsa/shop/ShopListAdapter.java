package com.example.joosulsa.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.R;

import java.util.ArrayList;
import java.util.List;


public class ShopListAdapter extends BaseAdapter {

    private Context context;
    private List<ShopListVO> dataList;

    public ShopListAdapter(Context context, List<ShopListVO> dataList){
        this.context =context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShopListViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_item_view, parent, false);

            viewHolder = new ShopListViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShopListViewHolder) convertView.getTag();
        }

        ShopListVO item = dataList.get(position);

        // TODO: 이미지 로딩 및 기타 데이터를 뷰에 설정

        viewHolder.getImg().setImageBitmap(item.getBitmap());
        viewHolder.getProTitle().setText(item.getTitle());
        viewHolder.getProInfo().setText(item.getContent());
        viewHolder.getProPrice().setText(String.valueOf(item.getPrice()));

        return convertView;

    }
}
