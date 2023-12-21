package com.example.joosulsa.category;

public class MainCategoryVO {
    // 필드 정의

    private int img;

    private String title;


    public MainCategoryVO(int img, String title) {
        this.img = img;
        this.title = title;
    }


    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
