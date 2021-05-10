package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchCosmeticData;
import com.example.cosmeticdiary.adapter.SearchCosmeticAdapter;

import java.util.ArrayList;

public class SearchCosmeticActivity extends AppCompatActivity {
    private ArrayList<SearchCosmeticData> cosmeticArray;
    private SearchCosmeticAdapter searchCosmeticAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cosmetic);

        ImageView backbtn = findViewById(R.id.iv_back);
        Button btnchoice = findViewById(R.id.btn_choice);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchCosmeticActivity.this, WritingActivity.class);
                startActivity(intent);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리사이클러뷰 선택완료 처리
            }
        });

        recyclerView = findViewById(R.id.rv_searchcosmetic);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        cosmeticArray = new ArrayList<>();

        searchCosmeticAdapter = new SearchCosmeticAdapter(cosmeticArray);
        recyclerView.setAdapter(searchCosmeticAdapter);

        cosmeticArray.add(new SearchCosmeticData(R.drawable.ic_launcher_background, "name", "brand"));
    }
}