package com.battlecrow.mimipizza;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChatsUpdater extends BroadcastReceiver {
    private final ChatFragmentCommon chatCommon;
    private final ChatsFragmentAdmin chatsAdmin;
    public ChatsUpdater(ChatFragmentCommon chatCommon, ChatsFragmentAdmin chatsAdmin) {
        this.chatCommon = chatCommon;
        this.chatsAdmin = chatsAdmin;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.battlecrow.UPDATE_CHATS".equals(intent.getAction())) {
            if(!CurrentUserData.isAdmin)
                chatCommon.updateChat();
            else {
                chatsAdmin.updateChats();
                chatsAdmin.updateChat();
            }
        }
    }
}
