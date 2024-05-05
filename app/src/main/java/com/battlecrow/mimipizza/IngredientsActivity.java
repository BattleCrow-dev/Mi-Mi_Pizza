package com.battlecrow.mimipizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.battlecrow.mimipizza.databinding.ActivityIngredientsBinding;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    private ActivityIngredientsBinding binding;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private IngredientAdapter adapter = new IngredientAdapter(ingredientList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityIngredientsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.buttonSmall.setOnClickListener(v -> {
            setButtonSizeAndTextSize(binding.buttonSmall, 48, 48, 32);
            setButtonSizeAndTextSize(binding.buttonMedium, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonLarge, 32, 32, 24);
        });

        binding.buttonMedium.setOnClickListener(v -> {
            setButtonSizeAndTextSize(binding.buttonSmall, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonMedium, 48, 48, 32);
            setButtonSizeAndTextSize(binding.buttonLarge, 32, 32, 24);
        });

        binding.buttonLarge.setOnClickListener(v -> {
            setButtonSizeAndTextSize(binding.buttonSmall, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonMedium, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonLarge, 48, 48, 32);
        });

        binding.imageButtonThin.setOnClickListener(v -> {
            setImageButtonSize(binding.imageButtonThin, 48, 48);
            setImageButtonSize(binding.imageButtonThick, 32, 32);
        });

        binding.imageButtonThick.setOnClickListener(v -> {
            setImageButtonSize(binding.imageButtonThin, 32, 32);
            setImageButtonSize(binding.imageButtonThick, 48, 48);
        });

        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));
        ingredientList.add(new Ingredient(R.drawable.logo, "Сырный борт", "199 руб."));

        binding.ingredientsList.setLayoutManager(new GridLayoutManager(this, 3));
        binding.ingredientsList.setAdapter(adapter);

        binding.imageViewBackArrow.setOnClickListener(v -> {
            finish();
        });

        binding.buttonAddToCart.setOnClickListener(v -> {
            finish();
        });
    }

    private void setButtonSizeAndTextSize(Button button, int widthDP, int heightDP, int textSizeSP) {
        Resources r = getResources();
        int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDP, r.getDisplayMetrics());
        int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDP, r.getDisplayMetrics());

        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        layoutParams.width = widthPx;
        layoutParams.height = heightPx;
        button.setLayoutParams(layoutParams);

        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSP);
    }

    private void setImageButtonSize(ImageButton button, int widthDP, int heightDP) {
        Resources r = getResources();
        int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDP, r.getDisplayMetrics());
        int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDP, r.getDisplayMetrics());

        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        layoutParams.width = widthPx;
        layoutParams.height = heightPx;
        button.setLayoutParams(layoutParams);
    }
}