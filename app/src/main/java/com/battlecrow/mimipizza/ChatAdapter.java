package com.battlecrow.mimipizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Message> {
    private final List<Message> messages;

    public ChatAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
        this.messages = messages;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (!CurrentUserData.isAdmin) {
            assert message != null;
            if (message.isSent())
                convertView = inflater.inflate(R.layout.item_message_sent, parent, false);
            else
                convertView = inflater.inflate(R.layout.item_message_received, parent, false);
        }
        else {
            if (message.isSent())
                convertView = inflater.inflate(R.layout.item_message_received, parent, false);
            else
                convertView = inflater.inflate(R.layout.item_message_sent, parent, false);
        }

        TextView textViewMessage;
        if (!CurrentUserData.isAdmin)
            textViewMessage = convertView.findViewById(message.isSent() ? R.id.textViewMessageSent : R.id.textViewMessageReceived);
        else {
            textViewMessage = convertView.findViewById(message.isSent() ? R.id.textViewMessageReceived : R.id.textViewMessageSent);
            ImageView img = convertView.findViewById(R.id.imageViewAvatar);
            img.setImageResource(message.isSent() ? R.drawable.avatar : R.drawable.bot);
        }
        textViewMessage.setText(message.getContent());

        return convertView;
    }

    public List<Message> getMessages(){
        return messages;
    }
    public List<String> getContents(){
        List<String> contents = new ArrayList<>();
        for (Message message : messages)
            contents.add(message.getContent());
        return contents;
    }
}