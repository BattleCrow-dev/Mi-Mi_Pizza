package com.battlecrow.mimipizza;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.battlecrow.mimipizza.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Fragment menuFragment;
    private Fragment catalogFragment;
    private Fragment cartFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        // Создаем экземпляры фрагментов
        menuFragment = new MenuFragment();
        catalogFragment = new HelpFragment();
        cartFragment = new CartFragment();

        // По умолчанию отображаем один из фрагментов (например, Меню)
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), menuFragment)
                .commit();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_item_1) {
                switchFragment(menuFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_item_2) {
                switchFragment(catalogFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_item_3) {
                switchFragment(cartFragment);
                return true;
            }

            return false;
        });
    }

    // Метод для замены текущего фрагмента
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }
}