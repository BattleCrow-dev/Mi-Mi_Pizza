package com.battlecrow.mimipizza;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.battlecrow.mimipizza.databinding.ItemProductBinding;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private final MenuFragment frag;

    public ProductAdapter(List<Product> productList, MenuFragment frag) {
        this.productList = productList;
        this.frag = frag;
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
        holder.binding.buttonBuy.setOnClickListener(v -> frag.openIngredients(product));
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

        @SuppressLint("SetTextI18n")
        public void bind(Product product) {
            binding.imageViewProduct.setImageResource(product.getImageResId());
            binding.textViewProductName.setText(product.getName());
            binding.textViewProductDescription.setText(product.getDescription());
            binding.textViewProductPrice.setText("Цена: от " + product.getPrice() + " руб.");
        }
    }
}

