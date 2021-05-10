package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cosmeticdiary.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView backbtn = findViewById(R.id.iv_back);
        Button btndupcheck = findViewById(R.id.btn_dupcheck);
        Button btncomplete = findViewById(R.id.btn_complete);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btndupcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 중복확인 리
            }
        });

        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리
            }
        });
    }
}