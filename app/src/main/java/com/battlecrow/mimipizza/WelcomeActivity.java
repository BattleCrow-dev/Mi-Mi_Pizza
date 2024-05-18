package com.battlecrow.mimipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityWelcomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;


public class WelcomeActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        com.battlecrow.mimipizza.databinding.ActivityWelcomeBinding binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        new Handler().postDelayed(() -> {
            if (userLoggedIn())
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            else
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        }, SPLASH_DELAY);
    }

    private boolean userLoggedIn() {
        boolean canEnter = false;
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        if(prefs.contains("login")) {
            CurrentUserData.accountLogin = prefs.getString("login", "");

            DatabaseReference database = FirebaseDatabase.getInstance().getReference("ChatData");
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CurrentUserData.chats.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        ChatData chat = snap.getValue(ChatData.class);
                        CurrentUserData.chats.add(chat);
                        assert chat != null;
                        if(Objects.equals(chat.getAuthor(), CurrentUserData.accountLogin)) {
                            CurrentUserData.chat = chat;
                        }
                    }

                    SharedPreferences prefs = getSharedPreferences("ChatData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    sendBroadcast(new Intent("com.battlecrow.UPDATE_CHATS"));

                    Gson gson = new Gson();
                    editor.putString("chat", gson.toJson(CurrentUserData.chat));
                    editor.putString("chats", gson.toJson(new Chats(CurrentUserData.chats)));
                    editor.apply();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            });

            canEnter = true;
        }

        if (prefs.contains("delivery_address")) {
            CurrentUserData.deliveryAddress = prefs.getString("delivery_address", "");
        }

        if (prefs.contains("is_admin")) {
            CurrentUserData.isAdmin = prefs.getBoolean("is_admin", false);
        }

        prefs = getSharedPreferences("ChatData", MODE_PRIVATE);

        if (prefs.contains("chat")) {
            Gson gson = new Gson();
            CurrentUserData.chat = gson.fromJson(prefs.getString("chat", ""), ChatData.class);
        }

        if (prefs.contains("chats")) {
            Gson gson = new Gson();
            Chats chats = gson.fromJson(prefs.getString("chats", ""), Chats.class);
            CurrentUserData.chats = chats.getChats();
        }

        return canEnter;
    }
}