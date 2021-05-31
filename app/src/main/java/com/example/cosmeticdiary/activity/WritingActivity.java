package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.CursorLoader;

import com.example.cosmeticdiary.DialogCheckDelete;
import com.example.cosmeticdiary.MySharedPreferences;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends AppCompatActivity {
    private DialogCheckDelete dialogCheckDelete;
    private TextView tv_ingredient;
    private EditText et_name;
    RadioGroup radioGroup;
    ImageView iv_writephoto;
    RetrofitService retrofitService;
    String imageBase64;
    Button btn_cancel;
    Button btn_right;
    Button btn_search;
    Button btn_edit;
    EditText et_write;
    CheckBox chkJopssal, chkDry, chkHwanongsung, chkGood, chkTrouble, chkEtc;
    ScrollView scrollView;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        iv_writephoto = findViewById(R.id.iv_writephoto);
        tv_ingredient = findViewById(R.id.tv_insert_ingredient);
        et_name = findViewById(R.id.et_cosmeticname);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_right = findViewById(R.id.btn_right);
        btn_search = findViewById(R.id.btn_search);
        btn_edit = findViewById(R.id.btn_edit);
        et_write = findViewById(R.id.et_write);
        radioGroup = findViewById(R.id.radiogp_satisfy);
        chkJopssal = findViewById(R.id.checkbox_jopssal);
        chkDry = findViewById(R.id.checkbox_dry);
        chkHwanongsung = findViewById(R.id.checkbox_hwanongsung);
        chkGood = findViewById(R.id.checkbox_good);
        chkTrouble = findViewById(R.id.checkbox_trouble);
        chkEtc = findViewById(R.id.checkbox_etc);
        scrollView = findViewById(R.id.scrollview_writing);
        constraintLayout = findViewById(R.id.constraintLayout_writing);

        //이미지뷰에 사진띄우기
        iv_writephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

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
                    retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                    String id = MySharedPreferences.getUserId(WritingActivity.this);
                    String cosmetic = et_name.getText().toString();
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(radioId);
                    //체크박스 설정.
                    String satisfy = rb.getText().toString();
                    String content = et_write.getText().toString();
                    String ingredient = tv_ingredient.getText().toString();
                    String jopssal = "false", dry = "false", hwanongsung = "false", good = "false", trouble = "false", etc = "false";
                    if (chkJopssal.isChecked()) jopssal = "true";
                    if (chkDry.isChecked()) dry = "true";
                    if (chkHwanongsung.isChecked()) hwanongsung = "true";
                    if (chkGood.isChecked()) good = "true";
                    if (chkTrouble.isChecked()) trouble = "true";
                    if (chkEtc.isChecked()) etc = "true";

                    Call<LoginModel> call = retrofitService.getWriting(id, cosmetic, imageBase64, satisfy, content,
                            ingredient, jopssal, dry, hwanongsung, good, trouble, etc);
                    call.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            if (response.isSuccessful()) {
                                Log.d("연결 성공", response.message());
                                LoginModel loginModel = response.body();
                                Log.v("code", loginModel.getCode());
                                System.out.println(loginModel.getCode() + loginModel.getSuccess());
                                if (loginModel.getCode().equals("100")) {
                                    Log.v("code", loginModel.getCode());
                                    System.out.println("success");
                                } else {
                                    Log.d("ssss", response.message());
                                }

                            } else if (response.code() == 404) {
                                Toast.makeText(WritingActivity.this, "인터넷 연결을 확인해주세요"
                                        , Toast.LENGTH_SHORT).show();
                                Log.d("ssss", response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            Log.d("ssss", t.getMessage());
                        }
                    });

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
        Intent intent = new Intent();
        Bitmap bm;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // MainActivity 에서 요청할 때 보낸 요청 코드 (1000)
                case 1000:
                    et_name.setText(data.getStringExtra("name"));
                    tv_ingredient.setText(data.getStringExtra("ingredient"));
                    break;
                case 2:
                    try {
                        // 선택한 이미지에서 비트맵 생성
                        String imageUrl = getRealPathFromUri(data.getData());
                        Uri uri = data.getData();
                        InputStream in = getContentResolver().openInputStream(data.getData());

                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        // 이미지 표시

                        Bitmap resized = Bitmap.createScaledBitmap(img, 256, 256, true);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);

                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                        Resources res = getResources();
                        imageBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);

                        iv_writephoto.setImageBitmap(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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


    private String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String url = cursor.getString(columnIndex);
        cursor.close();
        return url;
    }
//    // 바이너리 바이트 배열을 스트링으로
//    public static String byteArrayToBinaryString(byte[] b) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < b.length; ++i) {
//            sb.append(byteToBinaryString(b[i]));
//        }
//        return sb.toString();
//    } // 바이너리 바이트를 스트링으로
//
//    public static String byteToBinaryString(byte n) {
//        StringBuilder sb = new StringBuilder("00000000");
//        for (int bit = 0; bit < 8; bit++) {
//            if (((n >> bit) & 1) > 0) {
//                sb.setCharAt(7 - bit, '1');
//            }
//        }
//        return sb.toString();
//    }

}