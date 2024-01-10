package com.example.joosulsa.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joosulsa.ProductInfoActivity;
import com.example.joosulsa.R;

import java.io.ByteArrayOutputStream;
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

        // 데이터 넣어주기
        viewHolder.getImg().setImageBitmap(item.getImg());
        viewHolder.getProTitle().setText(item.getTitle());
        viewHolder.getProPrice().setText(String.valueOf(item.getPrice())+" Point");
        viewHolder.getProInfo().setText(item.getContent());


//        if (item.getImg() != null) {
//            // 이미지 데이터가 있는 경우
//            viewHolder.getImg().setVisibility(View.VISIBLE);
//            viewHolder.getImg().setImageBitmap(item.getImg());
//            viewHolder.getProTitle().setVisibility(View.GONE);
//            viewHolder.getProInfo().setVisibility(View.GONE);
//            viewHolder.getProPrice().setVisibility(View.GONE);
//        } else {
//            // 텍스트 데이터만 있는 경우
//            viewHolder.getImg().setVisibility(View.GONE);
//            viewHolder.getProTitle().setVisibility(View.VISIBLE);
//            viewHolder.getProInfo().setVisibility(View.VISIBLE);
//            viewHolder.getProPrice().setVisibility(View.VISIBLE);
//
//            viewHolder.getProTitle().setText(item.getTitle());
//            viewHolder.getProInfo().setText(item.getContent());
//            viewHolder.getProPrice().setText(String.valueOf(item.getPrice()));
//        }
        // productInfo 액티비티로 넘기는 이미지 비트맵 전처리
        Bitmap bitmap = item.getImg();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상세 페이지 액티비티 호출
                Intent intent = new Intent(context, ProductInfoActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("price", String.valueOf(item.getPrice()));
                intent.putExtra("prodImg", stream.toByteArray());
                Log.d("sendProdInfoData", item.getContent() + item.getPrice() + item.getTitle());
                context.startActivity(intent);
            }
        });

        return convertView;

    }


}
