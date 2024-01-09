package com.example.joosulsa.purchase_list;

public class PurchaseListVO {
    
    // 필드 정의
    private int img;
    private String title;
    private String content;
    private int date;

    // 생성자 생성
    public PurchaseListVO(int img, String title, String content, int date) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.date = date;
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

    public int getDate() {return date;}
}
