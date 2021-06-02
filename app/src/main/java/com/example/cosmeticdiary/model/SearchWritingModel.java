package com.example.cosmeticdiary.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import static com.example.cosmeticdiary.retrofit.Common.BASE_SERVER_URL;

public class SearchWritingModel {
    @SerializedName("id")
    String id;
    @SerializedName("cosmetic")
    String cosmetic;
    @SerializedName("photo")
    String img;
    @SerializedName("satisfy")
    String satisfy;
    @SerializedName("content")
    String content;
    @SerializedName("date")
    String date;
    @SerializedName("ingredient")
    String ingredient;
    @SerializedName("jopssal")
    String jopssal;
    @SerializedName("dry")
    String dry;
    @SerializedName("hwanongsung")
    String hwanongsung;
    @SerializedName("good")
    String good;
    @SerializedName("trouble")
    String trouble;
    @SerializedName("etc")
    String etc;

    public SearchWritingModel(String id, String cosmetic, String img, String satisfy, String content, String date, String ingredient, String jopssal, String dry, String hwanongsung, String good, String trouble, String etc) {
        this.id = id;
        this.cosmetic = cosmetic;
        this.img = img;
        this.satisfy = satisfy;
        this.content = content;
        this.date = date;
        this.ingredient = ingredient;
        this.jopssal = jopssal;
        this.dry = dry;
        this.hwanongsung = hwanongsung;
        this.good = good;
        this.trouble = trouble;
        this.etc = etc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCosmetic() {
        return cosmetic;
    }

    public void setCosmetic(String cosmetic) {
        this.cosmetic = cosmetic;
    }

    public String getImg() {
        Log.d("getimg", BASE_SERVER_URL+img);
        return BASE_SERVER_URL+img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(String satisfy) {
        this.satisfy = satisfy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getJopssal() {
        return jopssal;
    }

    public void setJopssal(String jopssal) {
        this.jopssal = jopssal;
    }

    public String getDry() {
        return dry;
    }

    public void setDry(String dry) {
        this.dry = dry;
    }

    public String getHwanongsung() {
        return hwanongsung;
    }

    public void setHwanongsung(String hwanongsung) {
        this.hwanongsung = hwanongsung;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getTrouble() {
        return trouble;
    }

    public void setTrouble(String trouble) {
        this.trouble = trouble;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
