package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

        ImageView backbtn = findViewById(R.id.iv_back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchWritingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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