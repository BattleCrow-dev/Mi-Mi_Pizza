package com.battlecrow.mimipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.firstEnterTextView.setOnClickListener(v -> startActivity(new Intent(this, RegistrationActivity.class)));

        binding.forgotPasswordTextView.setOnClickListener(v -> startActivity(new Intent(this, ForgotPasswordActivity.class)));

        binding.loginButton.setOnClickListener(v -> {
            database = FirebaseDatabase.getInstance().getReference("ChatData");

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CurrentUserData.chats.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        ChatData chat = snap.getValue(ChatData.class);
                        CurrentUserData.chats.add(chat);
                        assert chat != null;
                        if(Objects.equals(chat.getAuthor(), binding.usernameEditText.getText().toString())) {
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

            database = FirebaseDatabase.getInstance().getReference("UserData");
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean hasLogin = false;
                    boolean wrongPassword = false;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserData user = snap.getValue(UserData.class);

                        assert user != null;
                        if (Objects.equals(user.getAccountLogin(), binding.usernameEditText.getText().toString())) {
                            hasLogin = true;
                            if (binding.passwordEditText.getText().toString().equals(user.getAccountPassword())) {
                                if (user.isLogined())
                                    Toast.makeText(getApplicationContext(), "Данный аккаунт уже авторизован на другом устройстве", Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(getApplicationContext(), "Добро пожаловать, " + user.getAccountLogin(), Toast.LENGTH_SHORT).show();
                                    user.setLogined(true);
                                    database.child(Objects.requireNonNull(snap.getKey())).child("logined").setValue(true);
                                    CurrentUserData.init(user);
                                    SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("login", user.getAccountLogin());
                                    editor.putBoolean("is_admin", user.isAdmin());
                                    editor.apply();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            } else
                                wrongPassword = true;
                        }
                    }

                    if (!hasLogin) {
                        Toast.makeText(getApplicationContext(), "Пользователь с таким логином не найден", Toast.LENGTH_SHORT).show();
                    } else if (wrongPassword) {
                        Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}