package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.SearchCosmeticData;
import com.example.cosmeticdiary.adapter.SearchCosmeticAdapter;
import com.example.cosmeticdiary.model.LoginModel;
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
        Intent writing = getIntent();

        retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<LoginModel> call = retrofitService[0].getSearchCosmetic(writing.getStringExtra("cosmetic_name"));
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공", response.message());
                    LoginModel loginModel = response.body();
                    Log.d("code", loginModel.getCode());
                    Log.d("success", loginModel.getSuccess());
                    System.out.println(loginModel.getSuccess());
                } else if (response.code() == 404) {
                    Toast.makeText(SearchCosmeticActivity.this, "인터넷 연결을 확인해주세요"
                            , Toast.LENGTH_SHORT).show();
                    Log.d("ssss", response.message());

                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });


    ImageView backbtn = findViewById(R.id.iv_back);
    Button btnchoice = findViewById(R.id.btn_choice);

        backbtn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        Intent intent = new Intent(SearchCosmeticActivity.this, WritingActivity.class);
        startActivity(intent);
    }
    });

        backbtn.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        // 리사이클러뷰 선택완료 처리
    }
    });

    recyclerView = findViewById(R.id.rv_searchcosmetic);

    linearLayoutManager =new

    LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    cosmeticArray =new ArrayList<>();

    searchCosmeticAdapter =new

    SearchCosmeticAdapter(cosmeticArray);
        recyclerView.setAdapter(searchCosmeticAdapter);

        cosmeticArray.add(new

    SearchCosmeticData(R.drawable.ic_launcher_background, "name","brand"));
}
}