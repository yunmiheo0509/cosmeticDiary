package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("code")
    String code;

    @SerializedName("success")
    String success;


    public LoginModel(String code, String success) {
        this.code = code;
        this.success = success;
    }

    public String getUserID() {
        return code;
    }

    public void setUserID(String userID) {
        this.code = userID;
    }

    public String getLoginBy() {
        return success;
    }

    public void setLoginBy(String loginBy) {
        this.success = success;
    }
}
