package com.battlecrow.mimipizza;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
    private String author;
    private List<Message> messages;

    public ChatData() {
        author = "";
        messages = new ArrayList<>();
    }
    public ChatData(String author, List<Message> messages) {
        this.author = author;
        this.messages = messages;
    }
    public String getAuthor() {
        return author;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void addMessage (Message message) {
        messages.add(message);
    }
}

