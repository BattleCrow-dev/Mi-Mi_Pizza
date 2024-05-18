package com.battlecrow.mimipizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityRegistrationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {
    private ActivityRegistrationBinding binding;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance().getReference("UserData");

        binding.registerButton.setOnClickListener(v -> {
            if (!binding.usernameEditText.getText().toString().isEmpty()) {
                if (binding.passwordEditText.getText().toString().isEmpty() ||
                        binding.confirmPasswordEditText.getText().toString().isEmpty()){
                    Toast.makeText(this, "Введите пароли", Toast.LENGTH_SHORT).show();
                }
                else if (!binding.passwordEditText.getText().toString().equals(binding.confirmPasswordEditText.getText().toString())) {
                    Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.orderByChild("accountLogin").equalTo(binding.usernameEditText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Этот логин уже занят", Toast.LENGTH_SHORT).show();
                            } else {
                                UserData data = new UserData(binding.usernameEditText.getText().toString(),
                                        binding.passwordEditText.getText().toString(), false, false);
                                database.push().setValue(data);
                                Toast.makeText(getApplicationContext(), "Пользователь успешно зарегистрирован", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Firebase", "Ошибка при чтении данных", databaseError.toException());
                        }
                    });
                }
            }
            else {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            }
        });
    }
}