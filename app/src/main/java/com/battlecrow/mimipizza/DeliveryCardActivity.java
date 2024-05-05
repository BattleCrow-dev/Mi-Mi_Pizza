package com.battlecrow.mimipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.battlecrow.mimipizza.databinding.ActivityDeliveryCardBinding;

public class DeliveryCardActivity extends AppCompatActivity {
    private ActivityDeliveryCardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDeliveryCardBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}