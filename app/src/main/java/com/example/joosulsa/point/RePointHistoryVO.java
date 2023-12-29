package com.example.joosulsa.point;

public class RePointHistoryVO {

    // 필드 정의
    private String title;
    private String content;

    // 생성자
    public RePointHistoryVO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // getter 메소드
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
