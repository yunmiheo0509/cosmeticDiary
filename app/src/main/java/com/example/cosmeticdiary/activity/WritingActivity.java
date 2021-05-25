package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cosmeticdiary.DialogCheckDelete;
import com.example.cosmeticdiary.R;

import java.util.ArrayList;

public class WritingActivity extends AppCompatActivity {
    private DialogCheckDelete dialogCheckDelete;

    private TextView tv_ingredient;
    private EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        tv_ingredient = findViewById(R.id.tv_insert_ingredient);
        et_name = findViewById(R.id.et_cosmeticname);

        final Button btn_cancel = findViewById(R.id.btn_cancel);
        final Button btn_right = findViewById(R.id.btn_right);
        final Button btn_search = findViewById(R.id.btn_search);
        final Button btn_edit = findViewById(R.id.btn_edit);
        final EditText et_write = findViewById(R.id.et_write);

        final RadioGroup radioGroup = findViewById(R.id.radiogp_satisfy);
        final CheckBox chkJopssal = findViewById(R.id.checkbox_jopssal);

        final ScrollView scrollView = findViewById(R.id.scrollview_writing);
        final ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout_writing);

        Intent intent = getIntent();
        final String intentValue = intent.getStringExtra("main");
        final ArrayList intentArray = (ArrayList<String>) intent.getSerializableExtra("intentArray");

        // 글 작성
        if (intentValue.equals("plus")) {
            btn_edit.setVisibility(View.GONE);
            btn_right.setText("저장");
            btn_search.setVisibility(View.VISIBLE);

            // 취소 처리
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // 저장 처리
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 저장 후 메인화면으로

                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            btn_edit.setVisibility(View.VISIBLE);
            btn_search.setVisibility(View.GONE);
            btn_right.setText("삭제");


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
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // 수정
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    etname.setEnabled(true);
//                    etwrite.setEnabled(true);
//                    radioGroup.setEnabled(true);
//                    chkJopssal.setEnabled(true);

                    btn_edit.setVisibility(View.GONE);
                    btn_search.setVisibility(View.VISIBLE);
                    btn_right.setText("저장");

                    // 취소 처리
                    btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    // 수정 후 저장
                    btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 저장 처리
                            Toast.makeText(WritingActivity.this, "저장", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            // 삭제 처리
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 팝업창 확인 후 삭제
                    dialogCheckDelete = new DialogCheckDelete(WritingActivity.this, dialogListener);
                    dialogCheckDelete.show();
                }
            });
        }

        // 검색 처리
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritingActivity.this, SearchCosmeticActivity.class);
                startActivityForResult(intent, 1000);
//                startActivity(intent);
            }
        });

//        if (intentArray != null) {
//            et_name.setText(intentArray.get(0).toString());
//            tv_ingredient.setText(intentArray.get(1).toString());
//        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // MainActivity 에서 요청할 때 보낸 요청 코드 (1000)
                case 1000:
                    et_name.setText(data.getStringExtra("name"));
                    tv_ingredient.setText(data.getStringExtra("ingredient"));
                    break;
            }
        }
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