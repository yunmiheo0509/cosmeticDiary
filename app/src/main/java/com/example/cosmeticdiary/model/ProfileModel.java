package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;

    @SerializedName("gender")
    int gender;
    @SerializedName("age")
    int age;

    @SerializedName("image")
    String image;
    @SerializedName("skintype")
    String skintype;
    @SerializedName("allergy")
    String allergy;

    @SerializedName("alarm")
    int alarm;

    public ProfileModel(String id, String name, int gender, int age, String image, String skintype, String allergy, int alarm) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.image = image;
        this.skintype = skintype;
        this.allergy = allergy;
        this.alarm = alarm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSkintype() {
        return skintype;
    }

    public void setSkintype(String skintype) {
        this.skintype = skintype;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }
}
