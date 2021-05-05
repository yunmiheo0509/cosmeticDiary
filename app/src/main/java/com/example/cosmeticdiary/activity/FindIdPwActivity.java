package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cosmeticdiary.FindIdPwAdapter;
import com.example.cosmeticdiary.R;
import com.google.android.material.tabs.TabLayout;

public class FindIdPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_pw);

        ViewPager vp = findViewById(R.id.viewpager);
        FindIdPwAdapter adapter = new FindIdPwAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

//        adapter.notifyDataSetChanged();

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(vp);

        ImageView backbtn = findViewById(R.id.iv_back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindIdPwActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}