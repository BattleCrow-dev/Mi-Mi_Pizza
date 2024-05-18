package com.battlecrow.mimipizza;

public class Ingredient {
    private final int imageResource;
    private final String name;
    private final int price;

    public Ingredient(int imageResource, String name, int price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
