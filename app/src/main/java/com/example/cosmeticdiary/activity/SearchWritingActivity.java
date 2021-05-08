package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchCosmeticData;
import com.example.cosmeticdiary.SearchWritingData;
import com.example.cosmeticdiary.adapter.SearchCosmeticAdapter;
import com.example.cosmeticdiary.adapter.SearchWritingAdapter;

import java.util.ArrayList;

public class SearchWritingActivity extends AppCompatActivity {
    private ArrayList<SearchWritingData> writingArray;
    private SearchWritingAdapter searchWritingAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_writing);

        recyclerView = findViewById(R.id.rv_searchwriting);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        writingArray = new ArrayList<>();

        searchWritingAdapter = new SearchWritingAdapter(writingArray);
        recyclerView.setAdapter(searchWritingAdapter);

        writingArray.add(new SearchWritingData(R.drawable.ic_launcher_background, "2021.05.21",
                "name", "brand", "condition"));

    }
}