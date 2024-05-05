package com.battlecrow.mimipizza;

public class Message {
    private String content;
    private boolean sent;

    public Message(String content, boolean sent) {
        this.content = content;
        this.sent = sent;
    }

    public String getContent() {
        return content;
    }

    public boolean isSent() {
        return sent;
    }
}
