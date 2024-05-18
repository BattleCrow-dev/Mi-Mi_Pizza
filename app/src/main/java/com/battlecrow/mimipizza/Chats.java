package com.battlecrow.mimipizza;

import java.util.List;

public class Chats {
    private final List<ChatData> chats;

    public Chats(List<ChatData> chats) {
        this.chats = chats;
    }

    public List<ChatData> getChats() {
        return chats;
    }
}
