package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Objects;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;
    private final List<Product> productList = new ArrayList<>();
    private final ProductAdapter adapter = new ProductAdapter(productList, this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);

        binding.profileButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        binding.deliveryLocationButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DeliveryAddressActivity.class);
            startActivityForResult(intent, 200);
        });

        if (CurrentUserData.deliveryAddress.isEmpty())
            binding.deliveryLocationButton.setText("Введите адрес доставки!");
        else
            binding.deliveryLocationButton.setText(CurrentUserData.deliveryAddress + "\n" + getResources().getString(R.string.delivery_address));

        productList.clear();
        productList.add(new Product(R.drawable.peperoni, "Пепперони", "->Пикантная пепперони\n->Сыр Моцарелла\n->Томатный соус", 465));
        productList.add(new Product(R.drawable.bavarskaya, "Баварская", "->Охотничьи колбаски\n->Колбаса Чоризо\n->Корнишоны", 585));
        productList.add(new Product(R.drawable.gavaiskaya, "Гавайская", "->Ветчина\n->Ананасы\n->Сыр Моцарелла", 465));
        productList.add(new Product(R.drawable.derevenskaya, "Деревенская", "->Куриное филе\n->Болгарский перец\n->Помидоры\n->Чеснок", 505));
        productList.add(new Product(R.drawable.mehiko, "Мехико", "->Говядина\n->Халапеньо\n->Помидоры\n->Чеснок", 535));

        binding.recyclerViewProducts.setAdapter(adapter);
        binding.recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    public void openIngredients(Product product){
        Intent intent = new Intent(getContext(), IngredientsActivity.class);
        intent.putExtra("product", product);
        startActivityForResult(intent, 100);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("item")) {
                CartItem item = (CartItem) data.getSerializableExtra("item");
                ((MainActivity)requireActivity()).addItemToCart(item);
            }
        }
        else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra("delivery_address")) {
                binding.deliveryLocationButton.setText(data.getStringExtra("delivery_address")
                        + "\n" + getResources().getString(R.string.delivery_address));
                CurrentUserData.deliveryAddress = data.getStringExtra("delivery_address");

                SharedPreferences.Editor editor = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE).edit();
                editor.putString("delivery_address", data.getStringExtra("delivery_address"));
                editor.apply();
            }
        }
    }
}