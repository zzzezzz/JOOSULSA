package com.example.joosulsa.shop;

public class ShopListVO {

    // 필드 정의
    private int img;
    private int img2;
    private String title;
    private String title2;
    private String content;
    private String content2;
    private int price;
    private int price2;

    public ShopListVO(int img, int img2, String title, String title2, String content, String content2, int price, int price2) {
        this.img = img;
        this.img2 = img2;
        this.title = title;
        this.title2 = title2;
        this.content = content;
        this.content2 = content2;
        this.price = price;
        this.price2 = price2;
    }

    public int getImg() {
        return img;
    }

    public int getImg2() {
        return img2;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle2() {
        return title2;
    }

    public String getContent() {
        return content;
    }

    public String getContent2() {
        return content2;
    }

    public int getPrice() {
        return price;
    }

    public int getPrice2() {
        return price2;
    }
}
