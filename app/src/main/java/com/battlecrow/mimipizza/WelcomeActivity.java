package com.battlecrow.mimipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.battlecrow.mimipizza.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1000;

    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
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
        // Ваша логика проверки входа пользователя, например, можно проверить данные в кэше приложения
        // Здесь просто возвращаем false для примера
        return false;
    }
}