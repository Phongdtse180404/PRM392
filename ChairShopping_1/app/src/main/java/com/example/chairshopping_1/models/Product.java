package com.example.chairshopping_1.models;

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
