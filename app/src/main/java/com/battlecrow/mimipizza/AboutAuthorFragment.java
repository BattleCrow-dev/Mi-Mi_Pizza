package com.battlecrow.mimipizza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlecrow.mimipizza.databinding.FragmentAboutAuthorBinding;

public class AboutAuthorFragment extends Fragment {
    private FragmentAboutAuthorBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutAuthorBinding.inflate(inflater, container, false);

        binding.textViewEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + binding.textViewEmail.getText()));
            startActivity(intent);
        });

        return binding.getRoot();
    }
}