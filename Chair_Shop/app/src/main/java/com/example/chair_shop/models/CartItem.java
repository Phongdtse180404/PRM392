package com.example.chair_shop.models;

public class CartItem {
    public String name;
    public String description;
    public String price;
    public int imageResId;
    public int quantity;

    public CartItem(String name, String description, String price, int imageResId, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = quantity;
    }
}