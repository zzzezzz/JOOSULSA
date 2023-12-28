package com.example.joosulsa.history;

public class PointHistoryVO {

    // 필드 정의
    private String title;
    private String content;

    // 생성자 생성
    public PointHistoryVO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
