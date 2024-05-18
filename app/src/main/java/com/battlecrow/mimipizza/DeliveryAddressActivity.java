package com.battlecrow.mimipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.battlecrow.mimipizza.databinding.ActivityDeliveryAddressBinding;

public class DeliveryAddressActivity extends AppCompatActivity {
    private ActivityDeliveryAddressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.buttonConfirm.setOnClickListener(v -> {
            String street = binding.editTextStreet.getText().toString().trim();
            String house = binding.editTextHouse.getText().toString().trim();
            String apartment = binding.editTextApartment.getText().toString().trim();

            if(street.isEmpty() || house.isEmpty()) {
                Toast.makeText(this, "Адрес введён некорректно", Toast.LENGTH_SHORT).show();
            }
            else {
                String address = "Адрес доставки: ул. " + street + ", д. " + house;
                if (!apartment.isEmpty()) {
                    address += ", кв. " + apartment;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("delivery_address", address);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}