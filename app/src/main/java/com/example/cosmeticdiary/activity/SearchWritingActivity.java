package com.example.cosmeticdiary.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.util.MySharedPreferences;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.SearchWritingRecyclerAdapter;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.example.cosmeticdiary.model.SearchWritingModel;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWritingActivity extends AppCompatActivity {
    private List<SearchWritingModel> dataInfo;
    private SearchResultModel dataList;
    private SearchWritingRecyclerAdapter searchWritingAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    ImageView imgsearch, backBtn;
    EditText et_search;
    TextView nosearchresult;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_writing);
        imgsearch = findViewById(R.id.img_search);
        et_search = findViewById(R.id.et_searchwriting);
        backBtn = findViewById(R.id.iv_back);
        nosearchresult = findViewById(R.id.tv_nosearchresult);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rv_searchwriting);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //검색 실행
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                String id = MySharedPreferences.getUserId(SearchWritingActivity.this);
                Call<SearchResultModel> call = retrofitService.getSearchWriting(id,et_search.getText().toString());
                call.enqueue(new Callback<SearchResultModel>() {
                    @Override
                    public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
//                            SearchResultModel searchWritingResult = response.body();
//                            Log.d("검색", searchWritingResult.toString());
                            dataList = response.body();
                            dataInfo = dataList.writing_results;
                            if (response.body().getCode().equals("200")) {
                                searchWritingAdapter= new SearchWritingRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(searchWritingAdapter);
                                nosearchresult.setVisibility(View.GONE);
                            } else {
                                dataInfo.clear();
                                searchWritingAdapter= new SearchWritingRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(searchWritingAdapter);
                                Log.d("받아온거 없는경우다", dataInfo.toString());
                                nosearchresult.setVisibility(View.VISIBLE);
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