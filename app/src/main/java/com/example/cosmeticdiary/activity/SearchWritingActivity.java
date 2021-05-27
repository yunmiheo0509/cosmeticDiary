package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.SearchWritingRecyclerAdapter;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.example.cosmeticdiary.model.SearchWritingModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.cosmeticdiary.SearchCosmeticData;
//import com.example.cosmeticdiary.adapter.SearchCosmeticAdapter;

public class SearchWritingActivity extends AppCompatActivity {
    private List<SearchWritingModel> dataInfo;
    private SearchWritingRecyclerAdapter searchWritingAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    final RetrofitService[] retrofitService = new RetrofitService[1];
    SearchResultModel dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_writing);
        ImageView imgsearch = findViewById(R.id.img_search);
        final EditText et_search = findViewById(R.id.et_searchwriting);
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
//
//        dataInfo = new ArrayList<>();
//
//        searchWritingAdapter = new SearchWritingRecyclerAdapter(dataInfo);
//        recyclerView.setAdapter(searchWritingAdapter);
//
//        writingArray.add(new SearchWritingData(R.drawable.ic_launcher_background, "2021.05.21",
//                "name", "brand", "condition"));
        //검색 실행
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<SearchResultModel> call = retrofitService[0].getSearchWriting(et_search.getText().toString());

                call.enqueue(new Callback<SearchResultModel>() {
                    @Override
                    public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            SearchResultModel searchWritingResult = response.body();
                            Log.d("검색", searchWritingResult.toString());
                            dataList = response.body();
                            dataInfo = dataList.writing_results;
                            if (response.body().getCode().equals("200")) {
                                searchWritingAdapter= new SearchWritingRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(searchWritingAdapter);
                                Log.d("받아온거  확인", dataInfo.toString());
                            } else {
                                dataInfo.clear();
                                searchWritingAdapter= new SearchWritingRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(searchWritingAdapter);
                                Log.d("받아온거 없는경우다", dataInfo.toString());
                                Toast.makeText(SearchWritingActivity.this, "검색결과없음", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("ssss", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResultModel> call, Throwable t) {
                        Log.d("ssss", t.getMessage());
                    }
                });
            }
        });

    }
}