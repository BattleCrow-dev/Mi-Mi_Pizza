package com.battlecrow.mimipizza;

public class Ingredient {
    private int imageResource;
    private String name;
    private String price;

    public Ingredient(int imageResource, String name, String price) {
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

    public String getPrice() {
        return price;
    }
}
