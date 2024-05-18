package com.battlecrow.mimipizza;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.FragmentChatCommonBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ChatFragmentCommon extends Fragment {
    private FragmentChatCommonBinding binding;
    private ChatAdapter adapter;
    private DatabaseReference database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatCommonBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference("ChatData");
        adapter = new ChatAdapter(requireContext(), CurrentUserData.chat.getMessages());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.listViewChatMessages.setAdapter(adapter);
        updateChat();
    }

    public void updateChat() {
        for (Message message : CurrentUserData.chat.getMessages())
            if (!adapter.getContents().contains(message.getContent())) {
                adapter.add(message);
            }

        binding.listViewChatMessages.post(() -> binding.listViewChatMessages.smoothScrollToPosition(adapter.getCount() - 1));

        binding.buttonSendMessage.setOnClickListener(v -> {
            if(!binding.editTextMessage.getText().toString().isEmpty()) {
                if (adapter.getCount() == 0 || !adapter.getContents().get(adapter.getCount() - 1).equals(binding.editTextMessage.getText().toString())) {
                    Message message = new Message(binding.editTextMessage.getText().toString(), true);
                    adapter.add(message);
                    database.child(CurrentUserData.accountLogin).setValue(new ChatData(CurrentUserData.accountLogin, adapter.getMessages()));

                    SharedPreferences prefs = requireActivity().getSharedPreferences("ChatData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    Gson gson = new Gson();
                    editor.putString("chat", gson.toJson(CurrentUserData.chat));
                    editor.apply();

                    binding.editTextMessage.setText("");
                } else {
                    Toast.makeText(getActivity(), "Не спамь одинаковыми сообщениями!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(), "Хоть что-нибудь напиши", Toast.LENGTH_SHORT).show();
            }
        });
    }
}