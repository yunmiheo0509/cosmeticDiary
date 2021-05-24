package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class SearchWritingModel {
    @SerializedName("title")
    String title;
    @SerializedName("cosmetic")
    String cosmetic;
    @SerializedName("photo")
    String img;
    @SerializedName("satisfy")
    String satisfy;
    @SerializedName("date")
    String date;
    @SerializedName("skin_condition")
    String condition;
    @SerializedName("make")
    String brand;

    public String getBrand() {
        return brand;
    }

    public String getTitle() {
        return title;
    }

    public String getCosmetic() {
        return cosmetic;
    }

    public String getImg() {
        return img;
    }

    public String getSatisfy() {
        return satisfy;
    }

    public String getDate() {
        return date;
    }

    public String getCondition() {
        return condition;
    }

    public SearchWritingModel(String title, String cosmetic, String img, String satisfy, String date, String condition) {
        this.title = title;
        this.cosmetic = cosmetic;
        this.img = img;
        this.satisfy = satisfy;
        this.date = date;
        this.condition = condition;
    }
}
