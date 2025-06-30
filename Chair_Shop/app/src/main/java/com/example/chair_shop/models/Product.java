package com.example.chair_shop.models;

public class Product {
    public String name;
    public String price;
    public int imageResId;

    public Product(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
}
