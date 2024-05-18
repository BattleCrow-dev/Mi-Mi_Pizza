package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private final List<CartItem> items = new ArrayList<>();
    private int curFullPrice = 0;
    private int curFinalPrice = 0;
    private int skidka = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);

        CartAdapter adapter = new CartAdapter(getContext(), items, this);
        binding.listViewCartItems.setAdapter(adapter);

        binding.buttonPromoCode.setOnClickListener(v -> {
            if(binding.editTextPromoCode.getText().toString().equalsIgnoreCase("skidka")) {
                skidka = 100;
                changeSum(0);
                updateTexts();
                Toast.makeText(getContext(), "Вы ввели промокод на 100 рублей", Toast.LENGTH_SHORT).show();
            }
            else if(binding.editTextPromoCode.getText().toString().equalsIgnoreCase("pomogator")) {
                skidka = 100;
                changeSum(0);
                updateTexts();
                Toast.makeText(getContext(), "Вы ввели промокод на 100 рублей", Toast.LENGTH_SHORT).show();
            }
            else if(binding.editTextPromoCode.getText().toString().equalsIgnoreCase("mirea")) {
                skidka = 250;
                changeSum(0);
                updateTexts();
                Toast.makeText(getContext(), "Вы ввели промокод на 250 рублей", Toast.LENGTH_SHORT).show();
            }
            else {
                skidka = 0;
                changeSum(0);
                updateTexts();
                Toast.makeText(getContext(), "Нет такого промокода", Toast.LENGTH_SHORT).show();
            }

            binding.editTextPromoCode.setText("");
        });

        binding.buttonCheckout.setOnClickListener(v -> {
            if(curFullPrice >= 500) {
                if(CurrentUserData.deliveryAddress.isEmpty())
                    Toast.makeText(getContext(), "Вы не указали адрес доставки", Toast.LENGTH_SHORT).show();
                else {
                    ((MainActivity) requireActivity()).startDeliveryTimer();
                    clearCart();
                }
            }
            else
                Toast.makeText(getContext(), "Минимальная сумма заказа: 500 рублей", Toast.LENGTH_SHORT).show();
        });

        if(curFullPrice > 0)
            binding.emptyLayout.setVisibility(View.INVISIBLE);
        else
            binding.emptyLayout.setVisibility(View.VISIBLE);

        updateTexts();
        return binding.getRoot();
    }

    public void addItem(CartItem item) {
        boolean flag = false;
        for(CartItem it : items)
        {
            if(Objects.equals(it.getTitle(), item.getTitle()) &&
                    Objects.equals(it.getDescription(), item.getDescription()) &&
                    it.getImageResId() == item.getImageResId() && it.getPrice() == item.getPrice()) {
                it.setQuantity(it.getQuantity() + 1);
                flag = true;
            }
        }

        if (!flag)
            items.add(item);

        changeSum(item.getPrice());
    }

    public void changeSum(int value) {
        curFullPrice += value;
        curFinalPrice = Math.max(curFullPrice - skidka, 0);
    }

    @SuppressLint("SetTextI18n")
    public void updateTexts()
    {
        binding.textViewDiscount.setText(skidka + " руб.");
        binding.textViewTotalPrice.setText(curFinalPrice + " руб.");
        binding.textViewSubtotal.setText(curFullPrice + " руб.");
    }

    public void clearCart() {
        items.clear();
        curFinalPrice = 0;
        curFullPrice = 0;
        skidka = 0;
        updateTexts();
    }

    public void checkClearCart() {
        if(items.size() == 0)
            binding.emptyLayout.setVisibility(View.VISIBLE);
    }
}