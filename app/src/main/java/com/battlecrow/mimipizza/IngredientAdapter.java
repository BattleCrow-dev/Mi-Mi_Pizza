package com.battlecrow.mimipizza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.battlecrow.mimipizza.databinding.ItemIngredientBinding;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding = ItemIngredientBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;
        private boolean isHighlighted = false;

        public ViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                isHighlighted = !isHighlighted;
                if (isHighlighted)
                    binding.getRoot().setAlpha(0.5f);
                else
                    binding.getRoot().setAlpha(1.0f);
            });
        }

        public void bind(Ingredient ingredient) {
            binding.imageIngredient.setImageResource(ingredient.getImageResource());
            binding.textViewIngredientName.setText(ingredient.getName());
            binding.textViewIngredientPrice.setText(ingredient.getPrice());
        }
    }
}

