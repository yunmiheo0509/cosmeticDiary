package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cosmeticdiary.R;

public class ChangePwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        Button btncomplete = findViewById(R.id.btn_complete);
        ImageView backbtn = findViewById(R.id.iv_back);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인메뉴 화면으로 이동
            }
        });

        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 비밀번호 수정

                // 메인메뉴 화면으로 이동
//                Intent intent = new Intent(ChangePwActivity.this, .class);
//                startActivity(intent);
            }
        });
    }
}