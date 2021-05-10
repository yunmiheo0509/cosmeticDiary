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

    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
