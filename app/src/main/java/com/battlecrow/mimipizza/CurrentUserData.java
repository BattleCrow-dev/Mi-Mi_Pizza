package com.battlecrow.mimipizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrentUserData {
    public static String accountLogin = "";
    public static String deliveryAddress = "";
    public static boolean isAdmin = false;
    public static ChatData chat = new ChatData();
    public static List<ChatData> chats = new ArrayList<>();

    public static void init(UserData userData) {
        accountLogin = userData.getAccountLogin();
        deliveryAddress = "";
        isAdmin = userData.isAdmin();
    }

    public static void clear(){
        accountLogin = "";
        deliveryAddress = "";
        isAdmin = false;
        chat = new ChatData();
        chats = new ArrayList<>();
    }

    public static List<Message> getMessagesByAuthor(String author) {
        for (ChatData chatData : chats)
            if (Objects.equals(chatData.getAuthor(), author))
                return chatData.getMessages();

        return new ArrayList<>();
    }
}
