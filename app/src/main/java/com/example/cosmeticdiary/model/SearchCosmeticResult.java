package com.example.cosmeticdiary.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCosmeticResult {
    @SerializedName("results")
    List<SearchCosmeticModel> results;
}


