package com.battlecrow.mimipizza;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.battlecrow.mimipizza.databinding.ItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.imageViewProduct.setImageResource(product.getImageResId());
            binding.textViewProductName.setText(product.getName());
            binding.textViewProductDescription.setText(product.getDescription());
            binding.textViewProductPrice.setText(product.getPrice());
            binding.buttonBuy.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), IngredientsActivity.class);
                intent.putExtra("pizzaID", product.getImageResId());
                v.getContext().startActivity(intent);
            });
        }
    }
}

