package com.example.asus.FYP;

/**
 * Created by ASUS on 13/1/2018.
 */

public class ChatMessage {
    private String content;
    private boolean isMine;

    public ChatMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}

