package com.example.cosmeticdiary.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.ProfileModel;
import com.example.cosmeticdiary.util.MySharedPreferences;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    Button btn_changePw, btn_complete;
    ImageView iv_backbtn, iv_profileimg;
    CircleImageView iv_editimg;
    EditText et_name, et_age, et_allergy1, et_allergy2, et_allergy3, et_allergy4;
    String imageBase64 = null;
    RadioGroup radiogrp_gender, radiogrp_skintype;
    RadioButton radiobtn_male, radiobtn_female, radiobtn_dry, radiobtn_oily, radiobtn_combination, radiobtn_sensitive;
    String gender, skintype;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_changePw = findViewById(R.id.btn_changepw);
        btn_complete = findViewById(R.id.btn_complete);
        iv_editimg = findViewById(R.id.iv_editimg);
        iv_backbtn = findViewById(R.id.iv_back);

        iv_profileimg = findViewById(R.id.iv_profileimg);
        et_name = findViewById(R.id.et_name);

        radiogrp_gender = findViewById(R.id.radiogrp_gender);
        radiobtn_male = findViewById(R.id.radiobtn_male);
        radiobtn_female = findViewById(R.id.radiobtn_female);

        et_age = findViewById(R.id.et_userage);

        radiogrp_skintype = findViewById(R.id.radiogrp_skintype);
        radiobtn_dry = findViewById(R.id.radiobtn_dry);
        radiobtn_oily = findViewById(R.id.radiobtn_oily);
        radiobtn_combination = findViewById(R.id.radiobtn_combination);
        radiobtn_sensitive = findViewById(R.id.radiobtn_sensitive);

        et_allergy1 = findViewById(R.id.et_allergy1);
        et_allergy2 = findViewById(R.id.et_allergy2);
        et_allergy3 = findViewById(R.id.et_allergy3);
        et_allergy4 = findViewById(R.id.et_allergy4);

        //라디오버튼 리스너
        RadioGroup.OnCheckedChangeListener GenderChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobtn_male) gender = "남";
                else if (checkedId == R.id.radiobtn_female) gender = "여";
            }
        };

        RadioGroup.OnCheckedChangeListener SkintypeChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobtn_dry) skintype = "건성";
                else if (checkedId == R.id.radiobtn_oily) skintype = "지성";
                else if (checkedId == R.id.radiobtn_combination) skintype = "복합성";
                else if (checkedId == R.id.radiobtn_sensitive) skintype = "민감성";
            }
        };

        radiogrp_gender.setOnCheckedChangeListener(GenderChangeListener);
        radiogrp_skintype.setOnCheckedChangeListener(SkintypeChangeListener);

        //image
        et_name.setText(getIntent().getStringExtra("name"));

        if (!TextUtils.isEmpty(getIntent().getStringExtra("gender"))) {
            if (getIntent().getStringExtra("gender").equals("남")) {
                radiobtn_male.setChecked(true);
            } else if (getIntent().getStringExtra("gender").equals("여")) {
                radiobtn_female.setChecked(true);
            }
        }

        et_age.setText(getIntent().getStringExtra("age"));

        if (!TextUtils.isEmpty(getIntent().getStringExtra("skintype"))) {
            if (getIntent().getStringExtra("skintype").equals("건성")) {
                radiobtn_dry.setChecked(true);
            } else if (getIntent().getStringExtra("skintype").equals("지성")) {
                radiobtn_oily.setChecked(true);
            } else if (getIntent().getStringExtra("skintype").equals("복합성")) {
                radiobtn_combination.setChecked(true);
            } else if (getIntent().getStringExtra("skintype").equals("민감성")) {
                radiobtn_sensitive.setChecked(true);
            }
        }

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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // MainActivity 에서 요청할 때 보낸 요청 코드 (1000)
                case 2:
                    try {
                        // 선택한 이미지에서 비트맵 생성
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();
                        // 이미지 표시
                        Bitmap resized = Bitmap.createScaledBitmap(img, 256, 256, true);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        resized.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        imageBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);

//                        ExifInterface exif = null;
//                        try {
//                            exif = new ExifInterface(in); // path 파일 uri
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//                        Bitmap bmRotated = rotateBitmap(resized, orientation);

                        iv_profileimg.setImageBitmap(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    // 버튼 클릭시 데이터 저장처리
    private void editComplete() {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<LoginModel> call = retrofitService.EditProfile(MySharedPreferences.getUserId(EditProfileActivity.this),
                et_name.getText().toString(), gender, et_age.getText().toString(), skintype, et_allergy1.getText().toString());
        System.out.println(MySharedPreferences.getUserId(EditProfileActivity.this)+
                et_name.getText().toString()+ gender+ et_age.getText().toString()+ skintype+ et_allergy1.getText().toString());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공", response.message());
                    LoginModel loginModel = response.body();
//                    Log.v("code", loginModel.getCode());
//                    System.out.println(loginModel.getCode() + loginModel.getSuccess());
                    Toast.makeText(EditProfileActivity.this, "프로필 수정 완료", Toast.LENGTH_SHORT).show();
                    if (loginModel.getCode().equals("200")) {
                        Log.v("code", loginModel.getCode());
                        System.out.println("success");
                    } else
                        Log.d("ssss", response.message());
                    finish();
                } else if (response.code() == 404) {
                    Toast.makeText(EditProfileActivity.this, "인터넷 연결을 확인해주세요"
                            , Toast.LENGTH_SHORT).show();
                    Log.d("ssss", response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }

        });
    }
}