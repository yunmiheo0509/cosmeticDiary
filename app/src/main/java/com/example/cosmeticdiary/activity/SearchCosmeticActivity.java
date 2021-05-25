package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.SearchCosmeticRecyclerAdapter;
import com.example.cosmeticdiary.model.SearchCosmeticModel;
import com.example.cosmeticdiary.model.SearchResult;
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
    final RetrofitService[] retrofitService = new RetrofitService[1];
    SearchResult dataList;
    List<SearchCosmeticModel> dataInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cosmetic);

        ImageView backbtn = findViewById(R.id.iv_back);
        Button btnchoice = findViewById(R.id.btn_choice);
        final EditText et_search = findViewById(R.id.et_searchcosmetic);
        ImageView imgsearch = findViewById(R.id.img_search);

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

        //검색 실행
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<SearchResult> call = retrofitService[0].getSearchCosmetic(et_search.getText().toString());

                call.enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            SearchResult searchCosmeticResult = response.body();
                            Log.d("검색", searchCosmeticResult.toString());
                            dataList = response.body();
                            dataInfo = dataList.results;
                            if (response.body().getCode().equals("200")) {
                                recyclerAdapter = new SearchCosmeticRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(recyclerAdapter);
                                Log.d("받아온거  확인", dataInfo.toString());
                            } else {
                                dataInfo.clear();
                                recyclerAdapter = new SearchCosmeticRecyclerAdapter(getApplicationContext(), dataInfo);
                                recyclerView.setAdapter(recyclerAdapter);
                                Log.d("받아온거 없는경우다", dataInfo.toString());
                                Toast.makeText(SearchCosmeticActivity.this, "검색결과없음", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("ssss", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        Log.d("ssss", t.getMessage());
                    }
                });
            }
        });

        btnchoice.setOnClickListener(new View.OnClickListener() {
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