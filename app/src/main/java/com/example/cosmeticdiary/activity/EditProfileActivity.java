package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmeticdiary.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    Button btn_changePw, btn_complete;
    ImageView iv_backbtn;
    CircleImageView iv_editimg;
    EditText et_name, et_allergy1, et_allergy2, et_allergy3, et_allergy4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_changePw = findViewById(R.id.btn_changepw);
        btn_complete = findViewById(R.id.btn_complete);
        iv_editimg = findViewById(R.id.iv_editimg);
        iv_backbtn = findViewById(R.id.iv_back);

        et_name = findViewById(R.id.et_name);
        et_allergy1 = findViewById(R.id.et_allergy1);
        et_allergy2 = findViewById(R.id.et_allergy2);
        et_allergy3 = findViewById(R.id.et_allergy3);
        et_allergy4 = findViewById(R.id.et_allergy4);

        et_name.setText(getIntent().getStringExtra("name"));
        et_allergy1.setText(getIntent().getStringExtra("allergy"));

        btn_changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ChangePwActivity.class);
                startActivity(intent);
            }
        });

        iv_editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리 이동
            }
       });

        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editComplete();
            }
        });
    }

    // 버튼 클릭시 데이터 저장처리
    private void editComplete() {

    }
}