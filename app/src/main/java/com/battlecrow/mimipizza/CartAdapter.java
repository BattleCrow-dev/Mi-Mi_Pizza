package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {
    private final CartFragment frag;
    private final List<CartItem> cartItemList;
    public CartAdapter(Context context, List<CartItem> cartItemList, CartFragment frag) {
        super(context, 0, cartItemList);
        this.frag = frag;
        this.cartItemList = cartItemList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
        }

        CartItem cartItem = getItem(position);

        ImageView imageView = itemView.findViewById(R.id.imageViewProduct);
        TextView titleTextView = itemView.findViewById(R.id.textViewProductName);
        TextView descriptionTextView = itemView.findViewById(R.id.textViewProductDescription);
        TextView priceTextView = itemView.findViewById(R.id.textViewProductPrice);
        TextView quantityTextView = itemView.findViewById(R.id.textViewQuantity);
        TextView plusButton = itemView.findViewById(R.id.buttonIncrease);
        TextView minusButton = itemView.findViewById(R.id.buttonDecrease);

        if (cartItem != null) {
            imageView.setImageResource(cartItem.getImageResId());
            titleTextView.setText(cartItem.getTitle());
            descriptionTextView.setText(cartItem.getDescription() + cartItem.getIngredients());
            priceTextView.setText(cartItem.getQuantity() * cartItem.getPrice() + " руб.");
            quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

            plusButton.setOnClickListener(v -> {
                int quantity = cartItem.getQuantity() + 1;
                cartItem.setQuantity(quantity);
                notifyDataSetChanged();
                frag.changeSum(cartItem.getPrice());
                frag.updateTexts();
            });

            minusButton.setOnClickListener(v -> {
                if (cartItem.getQuantity() > 0) {
                    int quantity = cartItem.getQuantity() - 1;
                    cartItem.setQuantity(quantity);
                    notifyDataSetChanged();
                    frag.changeSum(-cartItem.getPrice());
                    frag.updateTexts();
                }

                if (cartItem.getQuantity() == 0) {
                    cartItemList.remove(position);
                    frag.checkClearCart();
                }
            });
        }

        return itemView;
    }
}

