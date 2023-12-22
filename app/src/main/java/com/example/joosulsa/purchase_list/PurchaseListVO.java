package com.example.joosulsa.purchase_list;

public class PurchaseListVO {
    
    // 필드 정의
    private int img;
    private String title;
    private String content;

    // 생성자 생성
    public PurchaseListVO(int img, String title, String content) {
        this.img = img;
        this.title = title;
        this.content = content;
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
}
