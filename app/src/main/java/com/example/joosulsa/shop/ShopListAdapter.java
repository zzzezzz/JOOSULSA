package com.example.joosulsa.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.ProductInfoActivity;
import com.example.joosulsa.R;

import java.io.File;
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
        viewHolder.getImg().setImageBitmap(item.getImg());
        viewHolder.getProTitle().setText(item.getTitle());
        viewHolder.getProInfo().setText(item.getContent());
        viewHolder.getProPrice().setText(String.valueOf(item.getPrice()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상세 페이지 액티비티 호출
                Intent intent = new Intent(context, ProductInfoActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("price", String.valueOf(item.getPrice()));
                context.startActivity(intent);
            }
        });

        return convertView;

    }


}
