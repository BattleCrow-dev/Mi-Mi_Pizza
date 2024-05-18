package com.battlecrow.mimipizza;

import java.io.Serializable;

public class CartItem implements Serializable {
    private final int imageResId;
    private final String title;
    private final String description;
    private final String ingredients;
    private final int price;
    private int quantity;

    public CartItem(int imageResId, String title, String description, String ingredients, int price, int quantity) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() { return description;}

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredients() {
        if (ingredients.isEmpty())
            return ingredients;
        else
            return "\n+" + ingredients;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

