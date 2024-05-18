package com.battlecrow.mimipizza;

public class Message {
    private final String content;
    private final boolean sent;

    public Message() {
        this.content = "";
        this.sent = false;
    }

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
