package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResultModel {
    @SerializedName("code")
    public String code;

    @SerializedName("results")
    public List<SearchCosmeticModel> results;

    @SerializedName("writing_results")
    public List<SearchWritingModel> writing_results;

    @SerializedName("calender_results")
    public List<SearchWritingModel> calender_results;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString(){
        return "results: " + calender_results;
    }
}


