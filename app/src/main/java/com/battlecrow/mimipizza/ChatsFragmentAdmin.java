package com.battlecrow.mimipizza;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.FragmentChatsAdminBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ChatsFragmentAdmin extends Fragment {
    private FragmentChatsAdminBinding binding;
    private ChatDataAdapter adapter;
    private ChatAdapter chatAdapter;
    private DatabaseReference database;
    private String currentUserChat = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatsAdminBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference("ChatData");
        adapter = new ChatDataAdapter(requireContext(), CurrentUserData.chats, this);
        chatAdapter = new ChatAdapter(requireContext(), CurrentUserData.chat.getMessages());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateChats();
    }

    public void closeChat() {
        binding.oneChatLayout.setVisibility(View.INVISIBLE);
        binding.chatsLayout.setVisibility(View.VISIBLE);
    }
    @SuppressLint("SetTextI18n")
    public void openChat(ChatData chatData) {
        binding.chatsLayout.setVisibility(View.INVISIBLE);
        binding.oneChatLayout.setVisibility(View.VISIBLE);
        currentUserChat = chatData.getAuthor();

        binding.textViewChatTitle.setText("Чат с " + currentUserChat);
        CurrentUserData.chat = chatData;

        chatAdapter = new ChatAdapter(requireContext(), CurrentUserData.chat.getMessages());
        binding.listViewChatMessages.setAdapter(chatAdapter);
        updateChat();
    }

    public void updateChat() {
        for (Message message : CurrentUserData.getMessagesByAuthor(currentUserChat))
            if (!chatAdapter.getContents().contains(message.getContent())) {
                chatAdapter.add(message);
            }

        binding.listViewChatMessages.post(() -> binding.listViewChatMessages.smoothScrollToPosition(chatAdapter.getCount() - 1));

        binding.imageViewBackArrow.setOnClickListener(v -> closeChat());
        binding.buttonSendMessage.setOnClickListener(v -> {
            if(!binding.editTextMessage.getText().toString().isEmpty()) {
                if (chatAdapter.getCount() == 0 || !chatAdapter.getContents().get(chatAdapter.getCount() - 1).equals(binding.editTextMessage.getText().toString())) {
                    Message message = new Message(binding.editTextMessage.getText().toString(), false);
                    chatAdapter.add(message);
                    database.child(currentUserChat).setValue(new ChatData(currentUserChat, chatAdapter.getMessages()));

                    SharedPreferences prefs = requireActivity().getSharedPreferences("ChatData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    Gson gson = new Gson();
                    editor.putString("chats", gson.toJson(new Chats(CurrentUserData.chats)));
                    editor.apply();

                    binding.editTextMessage.setText("");
                } else {
                    Toast.makeText(getActivity(), "Админка не даёт права спамить!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "Хоть что-нибудь напиши", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateChats() {
        binding.listViewChats.setAdapter(adapter);

    }
}