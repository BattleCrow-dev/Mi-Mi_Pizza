package com.battlecrow.mimipizza;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlecrow.mimipizza.databinding.FragmentChatAdminBinding;

public class ChatFragmentAdmin extends Fragment {
    private FragmentChatAdminBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatAdminBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}