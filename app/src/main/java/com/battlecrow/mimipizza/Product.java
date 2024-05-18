package com.battlecrow.mimipizza;

import java.io.Serializable;

public class Product implements Serializable {
    private final int imageResId;
    private final String name;
    private final String description;
    private final int price;
    private final int[] prices;
    private final String[] sizes = {"Маленькая 20см", "Средняя 30 см", "Большая 40 см"};
    private final String[] masses = {"350г.", "590г.", "700г."};
    private final String[] dough = {"тонкое тесто", "толстое тесто"};

    public Product(int imageResId, String name, String description, int price) {
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
        this.price = price;
        prices = new int[] {price, price + 150, price + 300};
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String[] getSizes() {
        return sizes;
    }

    public String[] getMasses() {
        return masses;
    }

    public String[] getDough() {
        return dough;
    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getIngredients() {
        String[] ingredients = description.split("->");
        String[] newArray = new String[ingredients.length - 1];
        System.arraycopy(ingredients, 1, newArray, 0, ingredients.length - 1);

        for (int i = 0; i < newArray.length; i++) {
            if(newArray[i].charAt(newArray[i].length() - 1) == '\n')
                newArray[i] = newArray[i].substring(0, newArray[i].length() - 1);
        }

        return newArray;
    }
}

