package com.battlecrow.mimipizza;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.FragmentChatCommonBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatFragmentCommon extends Fragment {
    private FragmentChatCommonBinding binding;
    private ChatAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatCommonBinding.inflate(inflater, container, false);

        List<Message> messages = getMessages();
        adapter = new ChatAdapter(getContext(), messages);
        binding.listViewChatMessages.setAdapter(adapter);

        binding.buttonSendMessage.setOnClickListener(v -> {
            binding.editTextMessage.setText("");
            Toast.makeText(getContext(), "Оно точно куда-то отправилось)", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    private List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();

        messages.add(new Message("Привет, мне приедет пицца?", true));
        messages.add(new Message("Здравствуйте, нет!", false));
        messages.add(new Message("ЧТООО", true));
        messages.add(new Message("Я ДЕНЬГИ ЗАПЛАТИЛ", true));
        messages.add(new Message("У нас нет механизма оплаты в приложении, вы доверились мошенникам...", false));
        messages.add(new Message("НЕ МОЖЕТ БЫТЬ. Я НЕ ТАКОЙ ТУПОЙ!!!", true));
        messages.add(new Message(".........................", true));
        messages.add(new Message(".........................", true));
        messages.add(new Message("Ладно, мой косяк, признаю...", true));

        return messages;
    }

}