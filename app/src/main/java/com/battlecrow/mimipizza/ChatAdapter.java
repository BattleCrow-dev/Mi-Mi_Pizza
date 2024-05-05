package com.battlecrow.mimipizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Message> {

    public ChatAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (message.isSent())
            convertView = inflater.inflate(R.layout.item_message_sent, parent, false);
        else
            convertView = inflater.inflate(R.layout.item_message_received, parent, false);

        TextView textViewMessage = convertView.findViewById(message.isSent() ? R.id.textViewMessageSent : R.id.textViewMessageReceived);
        textViewMessage.setText(message.getContent());

        return convertView;
    }
}