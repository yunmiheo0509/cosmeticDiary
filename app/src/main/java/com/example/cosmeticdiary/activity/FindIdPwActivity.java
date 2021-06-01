package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.adapter.FindIdPwAdapter;
import com.google.android.material.tabs.TabLayout;

public class FindIdPwActivity extends AppCompatActivity {
    ImageView iv_backbtn;

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

        iv_backbtn = findViewById(R.id.iv_back);

        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}