package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cosmeticdiary.DialogCheckDelete;
import com.example.cosmeticdiary.R;

public class WritingActivity extends AppCompatActivity {
    private DialogCheckDelete dialogCheckDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        final Button btncancel = findViewById(R.id.btn_cancel);
        final Button btnright = findViewById(R.id.btn_right);
        final Button btnsearch = findViewById(R.id.btn_search);
        final Button btnedit = findViewById(R.id.btn_edit);

        final EditText etname = findViewById(R.id.et_cosmeticname);
        final EditText etwrite = findViewById(R.id.et_write);
        final RadioGroup radioGroup = findViewById(R.id.radiogp_satisfy);
        final CheckBox chkJopssal = findViewById(R.id.checkbox_jopssal);

        final ScrollView scrollView = findViewById(R.id.scrollview_writing);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout_writing);

        Intent intent = getIntent();
        final String intentValue = intent.getStringExtra("main");

        // 글 작성
        if (intentValue.equals("plus")) {
            btnedit.setVisibility(View.GONE);
            btnright.setText("저장");
            btnsearch.setVisibility(View.VISIBLE);

            // 취소 처리
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // 저장 처리
            btnright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 저장 후 메인화면으로

                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            btnedit.setVisibility(View.VISIBLE);
            btnsearch.setVisibility(View.GONE);
            btnright.setText("삭제");


//            constraintLayout.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;
//                }
//            });
//            etname.setEnabled(false);
//            etwrite.setEnabled(false);
//            radioGroup.setEnabled(false);
//            chkJopssal.setEnabled(false);

            // 취소 처리
            btnright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // 수정
            btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    etname.setEnabled(true);
//                    etwrite.setEnabled(true);
//                    radioGroup.setEnabled(true);
//                    chkJopssal.setEnabled(true);

                    btnedit.setVisibility(View.GONE);
                    btnsearch.setVisibility(View.VISIBLE);
                    btnright.setText("저장");

                    // 취소 처리
                    btnright.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    // 수정 후 저장
                    btnright.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 저장 처리
                            Toast.makeText(WritingActivity.this, "저장", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            // 삭제 처리
            btnright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 팝업창 확인 후 삭제
                    dialogCheckDelete = new DialogCheckDelete(WritingActivity.this, dialogListener);
                    dialogCheckDelete.show();
                }
            });
        }

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritingActivity.this, SearchCosmeticActivity.class);
                startActivity(intent);
            }
        });
    }

    //다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_ok:
                    // 로그아웃 진행
                    Toast.makeText(WritingActivity.this, "삭제", Toast.LENGTH_SHORT).show();
                case R.id.tv_cancel:
                    dialogCheckDelete.dismiss();
            }
        }
    };
}