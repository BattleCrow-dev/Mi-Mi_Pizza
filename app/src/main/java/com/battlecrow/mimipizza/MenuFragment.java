package com.battlecrow.mimipizza;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.battlecrow.mimipizza.databinding.FragmentMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter adapter = new ProductAdapter(productList);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        binding.profileButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        binding.deliveryLocationButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DeliveryCardActivity.class));
        });

        productList.add(new Product(R.drawable.logo, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", "Цена: от 489 руб."));
        productList.add(new Product(R.drawable.logo, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", "Цена: от 489 руб."));
        productList.add(new Product(R.drawable.logo, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", "Цена: от 489 руб."));
        productList.add(new Product(R.drawable.logo, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", "Цена: от 489 руб."));
        productList.add(new Product(R.drawable.logo, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", "Цена: от 489 руб."));

        binding.recyclerViewProducts.setAdapter(adapter);
        binding.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}