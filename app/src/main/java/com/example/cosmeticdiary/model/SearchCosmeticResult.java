package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCosmeticResult {
    @SerializedName("code")
    public String code;

    @SerializedName("results")
    public List<SearchCosmeticModel> results;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString(){
        return "results: "+results;
    }

}


