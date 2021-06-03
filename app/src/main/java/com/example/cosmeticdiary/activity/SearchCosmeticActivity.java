package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.SearchCosmeticRecyclerAdapter;
import com.example.cosmeticdiary.model.SearchCosmeticModel;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCosmeticActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    SearchCosmeticRecyclerAdapter recyclerAdapter;
    SearchResultModel dataList;
    List<SearchCosmeticModel> dataInfo;
    EditText et_search;
    RetrofitService retrofitService;

    ImageView backbtn, imgsearch;
    Button btn_choice;
    TextView nosearchresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cosmetic);

        backbtn = findViewById(R.id.iv_back);
        btn_choice = findViewById(R.id.btn_choice);
        et_search = findViewById(R.id.et_searchcosmetic);
        imgsearch = findViewById(R.id.img_search);
        nosearchresult = findViewById(R.id.tv_nosearchresult);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rv_searchcosmetic);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //검색 실행
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerAdapter = null;
                retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                String name = et_search.getText().toString();
                Call<SearchResultModel> call = retrofitService.getSearchCosmetic(name);
                Log.d("et_search에 입력된거",name);
                call.enqueue(new Callback<SearchResultModel>() {
                    @Override
                    public void onResponse(Call<SearchResultModel> call, Response<SearchResultModel> response) {
                        Log.d("연결 성공", response.message());
                        SearchResultModel searchCosmeticResult = response.body();
                        Log.d("검색", searchCosmeticResult.toString());
                        dataList = response.body();
                        dataInfo = dataList.results;
                        if (response.body().getCode().equals("200")) {
                            recyclerAdapter = new SearchCosmeticRecyclerAdapter(getApplicationContext(), dataInfo);
                            recyclerView.setAdapter(recyclerAdapter);
                            Log.d("받아온거  확인", dataInfo.toString());
                            nosearchresult.setVisibility(View.GONE);
                            btn_choice.setEnabled(true);
                        } else {
                            dataInfo.clear();
                            recyclerAdapter = new SearchCosmeticRecyclerAdapter(getApplicationContext(), dataInfo);
                            recyclerView.setAdapter(recyclerAdapter);
                            Log.d("받아온거 없는경우다", dataInfo.toString());
                            nosearchresult.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResultModel> call, Throwable t) {
                        Log.d("ssss", t.getMessage());
                    }
                });
            }
        });

        btn_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", recyclerAdapter.choice().get(0));
                intent.putExtra("ingredient", recyclerAdapter.choice().get(1));
                setResult(RESULT_OK, intent);

                Log.d("반환", recyclerAdapter.choice().get(0) +" "+ recyclerAdapter.choice().get(1));

                finish();
            }
        });
    }
}