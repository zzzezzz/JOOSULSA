package com.example.joosulsa.shop;

import android.graphics.Bitmap;

public class ShopListVO {

    // 필드 정의
    private String img;
    private String title;
    private String content;
    private int price;
    private Bitmap bitmap;

    public ShopListVO(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getPrice() {
        return price;
    }
}
