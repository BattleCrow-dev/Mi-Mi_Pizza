package com.battlecrow.mimipizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.battlecrow.mimipizza.databinding.ActivityIngredientsBinding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity {
    private ActivityIngredientsBinding binding;
    private final List<Ingredient> ingredientList = new ArrayList<>();
    private final IngredientAdapter adapter = new IngredientAdapter(ingredientList, this);
    private Product curProduct;
    private String[] curTexts = {"размер", "толщина", "вес", "цена"};
    private int addedPrice = 0;

    private final List<String> chosenIngredients = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityIngredientsBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        curProduct = getIntent().getSerializableExtra("product", Product.class);
        assert curProduct != null;
        curTexts = new String[] {curProduct.getSizes()[0],
                curProduct.getDough()[0],
                curProduct.getMasses()[0],
                Integer.toString(curProduct.getPrices()[0])};
        int curPrice = curProduct.getPrice();

        binding.buttonSmall.setOnClickListener(v -> {
            curTexts[0] = curProduct.getSizes()[0];
            curTexts[2] = curProduct.getMasses()[0];
            curTexts[3] = Integer.toString(curProduct.getPrices()[0] + addedPrice);
            setButtonSizeAndTextSize(binding.buttonSmall, 48, 48, 32);
            setButtonSizeAndTextSize(binding.buttonMedium, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonLarge, 32, 32, 24);
            updateTextViews();
        });

        binding.buttonMedium.setOnClickListener(v -> {
            curTexts[0] = curProduct.getSizes()[1];
            curTexts[2] = curProduct.getMasses()[1];
            curTexts[3] = Integer.toString(curProduct.getPrices()[1] + addedPrice);
            setButtonSizeAndTextSize(binding.buttonSmall, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonMedium, 48, 48, 32);
            setButtonSizeAndTextSize(binding.buttonLarge, 32, 32, 24);
            updateTextViews();
        });

        binding.buttonLarge.setOnClickListener(v -> {
            curTexts[0] = curProduct.getSizes()[2];
            curTexts[2] = curProduct.getMasses()[2];
            curTexts[3] = Integer.toString(curProduct.getPrices()[2] + addedPrice);
            setButtonSizeAndTextSize(binding.buttonSmall, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonMedium, 32, 32, 24);
            setButtonSizeAndTextSize(binding.buttonLarge, 48, 48, 32);
            updateTextViews();
        });

        binding.imageButtonThin.setOnClickListener(v -> {
            curTexts[1] = curProduct.getDough()[0];
            setImageButtonSize(binding.imageButtonThin, 48, 48);
            setImageButtonSize(binding.imageButtonThick, 32, 32);
            updateTextViews();
        });

        binding.imageButtonThick.setOnClickListener(v -> {
            curTexts[1] = curProduct.getDough()[1];
            setImageButtonSize(binding.imageButtonThin, 32, 32);
            setImageButtonSize(binding.imageButtonThick, 48, 48);
            updateTextViews();
        });

        ingredientList.add(new Ingredient(R.drawable.sir_bort, "Сырный борт", 99));
        ingredientList.add(new Ingredient(R.drawable.gribi, "Грибы", 79));
        ingredientList.add(new Ingredient(R.drawable.vetchina, "Ветчина", 89));
        ingredientList.add(new Ingredient(R.drawable.tomati, "Томаты", 69));
        ingredientList.add(new Ingredient(R.drawable.ananas, "Ананасы", 129));
        ingredientList.add(new Ingredient(R.drawable.perec, "Перец чили", 99));
        ingredientList.add(new Ingredient(R.drawable.zhelud, "Жёлуди", 119));
        ingredientList.add(new Ingredient(R.drawable.goroh, "Горох", 59));

        binding.ingredientsList.setLayoutManager(new GridLayoutManager(this, 3));
        binding.ingredientsList.setAdapter(adapter);

        binding.imageViewPizza.setImageResource(curProduct.getImageResId());
        binding.textViewIngredients.setText("Ингредиенты: " + String.join(", ", curProduct.getIngredients()));
        binding.textViewPizzaName.setText(curProduct.getName());
        binding.textViewPizzaDetails.setText(String.join(", ",
                new String[] {curTexts[0], curTexts[1], curTexts[2]}));
        binding.buttonAddToCart.setText("В корзину за " + curPrice + " руб.");

        binding.imageViewBackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });

        binding.buttonAddToCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            CartItem item = new CartItem(curProduct.getImageResId(), curProduct.getName(),
                    binding.textViewPizzaDetails.getText().toString(),
                    String.join("\n+", chosenIngredients),
                    Integer.parseInt(curTexts[3]), 1);
            intent.putExtra("item", item);
            setResult(Activity.RESULT_OK, intent);
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

    @SuppressLint("SetTextI18n")
    private void updateTextViews() {
        binding.textViewPizzaDetails.setText(String.join(", ", new String[] {curTexts[0], curTexts[1], curTexts[2]}));
        binding.buttonAddToCart.setText("В корзину за " + curTexts[3] + " руб.");
    }

    public void addAddedPrice(int value, String ingredient) {
        addedPrice += value;
        curTexts[3] = Integer.toString(Integer.parseInt(curTexts[3]) + value);

        if (value > 0)
            chosenIngredients.add(ingredient + " (" + value + " руб.)");
        else
            chosenIngredients.remove(ingredient + " (" + -value + " руб.)");

        updateTextViews();
    }
}