package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class SearchCosmeticModel {
    @SerializedName("img")
    int img;
    @SerializedName("name")
    String name;
    @SerializedName("brand")
    String brand;

    public SearchCosmeticModel(int img, String name, String brand) {
        this.img = img;
        this.name = name;
        this.brand = brand;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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
}
