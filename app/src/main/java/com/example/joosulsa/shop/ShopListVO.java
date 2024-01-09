package com.example.joosulsa.shop;

import android.graphics.Bitmap;

public class ShopListVO {

    // 필드 정의
    private Bitmap img;
    private String title;
    private String content;
    private int price;

    public ShopListVO(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public ShopListVO(Bitmap img) {
        this.img = img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
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
