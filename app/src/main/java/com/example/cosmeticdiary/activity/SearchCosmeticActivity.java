package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchCosmeticData;
import com.example.cosmeticdiary.adapter.SearchCosmeticAdapter;
import com.example.cosmeticdiary.model.SearchCosmeticModel;
import com.example.cosmeticdiary.model.SearchCosmeticResult;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCosmeticActivity extends AppCompatActivity {
    private ArrayList<SearchCosmeticData> cosmeticArray;
    private SearchCosmeticAdapter searchCosmeticAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    final RetrofitService[] retrofitService = new RetrofitService[1];

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

        cosmeticArray = new ArrayList<>();

        searchCosmeticAdapter = new SearchCosmeticAdapter(cosmeticArray);
        recyclerView.setAdapter(searchCosmeticAdapter);

        //검색 실행
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<SearchCosmeticResult> call = retrofitService[0].getSearchCosmetic(et_search.getText().toString());

                call.enqueue(new Callback<SearchCosmeticResult>() {
                    @Override
                    public void onResponse(Call<SearchCosmeticResult> call, Response<SearchCosmeticResult> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            SearchCosmeticResult searchCosmeticResult = response.body();
                            Log.v("검색", searchCosmeticResult.toString());
//                            if (searchCosmeticModel.getCode().equals("200")) {
//                                Toast.makeText(LoginActivity.this, "로그인 되었습니다.".toString(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
                        } else {
                            Log.d("ssss", response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchCosmeticResult> call, Throwable t) {
                        Log.d("ssss", t.getMessage());
                    }
                });
            }
        });

        cosmeticArray.add(new SearchCosmeticData(R.drawable.ic_launcher_background, "name", "brand"));
    }
}