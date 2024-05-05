package com.battlecrow.mimipizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.battlecrow.mimipizza.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProfileBinding.inflate(getLayoutInflater());

        binding.imageViewBackArrow.setOnClickListener(v -> finish());

        binding.exitAccountTextView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View customView = getLayoutInflater().inflate(R.layout.dialog_exit_account, null);
            builder.setView(customView);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button buttonClose = customView.findViewById(R.id.buttonNo);
            Button buttonSend = customView.findViewById(R.id.buttonYes);

            buttonClose.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            buttonSend.setOnClickListener(v12 -> {
                startActivity(new Intent(this, LoginActivity.class));
                finishAffinity();
                dialog.dismiss();
            });
        });

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}