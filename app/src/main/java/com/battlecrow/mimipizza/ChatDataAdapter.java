package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChatDataAdapter extends ArrayAdapter<ChatData> {
    private final ChatsFragmentAdmin frag;
    private final List<ChatData> chatsList;
    public ChatDataAdapter(Context context, List<ChatData> chatsList, ChatsFragmentAdmin frag) {
        super(context, 0, chatsList);
        this.frag = frag;
        this.chatsList = chatsList;
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, parent, false);
        }

        ChatData chat = getItem(position);

        TextView loginTextView = itemView.findViewById(R.id.textViewLogin);

        if (chat != null) {
            loginTextView.setText("Логин: " + chat.getAuthor());
        }

        itemView.setOnClickListener(v -> frag.openChat(chat));

        return itemView;
    }
}
