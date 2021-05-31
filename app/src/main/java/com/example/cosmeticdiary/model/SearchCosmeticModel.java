package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class SearchCosmeticModel {
    @SerializedName("img")
    String img;
    @SerializedName("name")
    String name;
    @SerializedName("make")
    String brand;
    @SerializedName("ingredient")
    String ingredient;

    public SearchCosmeticModel(String img, String name, String brand, String ingredient) {
        this.img = img;
        this.name = name;
        this.brand = brand;
        this.ingredient = ingredient;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
