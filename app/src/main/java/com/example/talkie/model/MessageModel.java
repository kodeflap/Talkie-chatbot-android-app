package com.example.talkie.model;

public class MessageModel {
    public MessageModel(String cnt) {
        this.cnt = cnt;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    private String cnt;
}
