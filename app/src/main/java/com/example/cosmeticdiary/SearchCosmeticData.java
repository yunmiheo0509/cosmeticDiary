package com.example.cosmeticdiary;

public class SearchCosmeticData {
    private String img;
    private String name;
    private String brand;

    public SearchCosmeticData(String img, String name, String brand) {
        this.img = img;
        this.name = name;
        this.brand = brand;
    }

    public String getImg() { return img; }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
