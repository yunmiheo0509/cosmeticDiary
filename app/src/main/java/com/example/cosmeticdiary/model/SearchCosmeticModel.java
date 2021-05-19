package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class SearchCosmeticModel {
    @SerializedName("img")
    String img;
    @SerializedName("name")
    String name;
    @SerializedName("make")
    String brand;

    public SearchCosmeticModel(String img, String name, String brand) {
        this.img = img;
        this.name = name;
        this.brand = brand;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

}
