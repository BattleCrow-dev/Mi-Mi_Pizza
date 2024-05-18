package com.battlecrow.mimipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance().getReference("UserData");

        binding.changePasswordButton.setOnClickListener(v -> {
            if(binding.usernameEditText.getText().toString().isEmpty())
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            else {
                if (binding.passwordEditText.getText().toString().isEmpty() ||
                        binding.confirmPasswordEditText.getText().toString().isEmpty())
                    Toast.makeText(this, "Введите пароли", Toast.LENGTH_SHORT).show();
                else if (!binding.passwordEditText.getText().toString().equals(binding.confirmPasswordEditText.getText().toString()))
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                else {
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean hasLogin = false;
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                UserData user = snap.getValue(UserData.class);

                                assert user != null;
                                if (Objects.equals(user.getAccountLogin(), binding.usernameEditText.getText().toString())) {
                                    hasLogin = true;

                                    if(user.isAdmin())
                                        Toast.makeText(getApplicationContext(), "Вы не можете поменять пароль админу!", Toast.LENGTH_SHORT).show();
                                    else {
                                        database.child(Objects.requireNonNull(snap.getKey())).child("accountPassword").setValue(binding.passwordEditText.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                    }
                                }
                            }

                            if (!hasLogin) {
                                Toast.makeText(getApplicationContext(), "Пользователь с таким логином не найден", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}