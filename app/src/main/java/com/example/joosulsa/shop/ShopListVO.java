package com.example.joosulsa.shop;

public class ShopListVO {

    // 필드 정의
    private int img;
    private String title;
    private String content;
    private int price;

    public ShopListVO(int img, String title, String content, int price) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public int getImg() {
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
