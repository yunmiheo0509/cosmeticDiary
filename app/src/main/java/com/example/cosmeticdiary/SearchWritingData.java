package com.example.cosmeticdiary;

public class SearchWritingData {
    private int img;
    private String date;
    private String name;
    private String brand;
    private String condition;

    public SearchWritingData(int img, String date, String name, String brand, String condition) {
        this.img = img;
        this.date = date;
        this.name = name;
        this.brand = brand;
        this.condition = condition;
    }

    public int getImg() { return img; }

    public String getDate() { return date; }

    public String getName() { return name; }

    public String getBrand() { return brand; }

    public String getCondition() { return condition; }

    public void setImg(int img) { this.img = img; }

    public void setDate(String date) { this.date = date; }

    public void setName(String name) { this.name = name; }

    public void setBrand(String brand) { this.brand = brand; }

    public void setCondition(String condition) { this.condition = condition; }
}
