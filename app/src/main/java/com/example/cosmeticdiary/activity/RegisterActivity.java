package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    boolean flag_id = false;
    boolean flag_pw = false;
    EditText et_id, et_password, et_name, et_email, et_checkpw;
    TextView tv_passwordCheckMsg, tv_idCheckMsg;
    ImageView backbtn;
    Button btndupcheck, btncomplete;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_checkpw = findViewById(R.id.et_checkpw);
        tv_passwordCheckMsg = findViewById(R.id.tv_passwordCheckMsg);
        tv_idCheckMsg = findViewById(R.id.tv_idCheckMsg);

        backbtn = findViewById(R.id.iv_back);
        btndupcheck = findViewById(R.id.btn_dupcheck);
        btncomplete = findViewById(R.id.btn_complete);


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
                // ????????????
                retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Log.d("????????????", "???????????? ????????????");
                Call<LoginModel> call = retrofitService.getDoubleCheck(et_id.getText().toString());
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("?????? ??????", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
//                            Log.v("success", loginModel.getSuccess());
                            String code = loginModel.getCode();
                            if (code.equals("100")) {
                                Toast.makeText(RegisterActivity.this, "???????????? ???????????????.".toString(), Toast.LENGTH_SHORT).show();
                                tv_idCheckMsg.setText("???????????? ???????????????");
                            } else if (code.equals("300")) {
                                tv_idCheckMsg.setText("??????????????? ??????????????????.");
                                tv_idCheckMsg.setTextColor(Color.BLUE);
                                flag_id = true;
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(RegisterActivity.this, "????????? ????????? ??????????????????"
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
        });

        et_checkpw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw1=et_password.getText().toString();
                if (Pattern.matches("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$", s.toString())) {
                    if (s.toString().equals(pw1)) {
                        tv_passwordCheckMsg.setText("???????????????");
                        tv_passwordCheckMsg.setTextColor(Color.BLUE);
                        flag_pw = true;
                    } else {
                        tv_passwordCheckMsg.setText("???????????? ????????? ???????????????");
                        tv_passwordCheckMsg.setTextColor(Color.RED);
                        flag_pw = false;
                        Log.d("pw1", pw1);
                        Log.d("pw2", s.toString());
                    }
                } else {
                    tv_passwordCheckMsg.setText("???????????? ????????? ??????????????????");
                    tv_passwordCheckMsg.setTextColor(Color.RED);
                    flag_pw = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ???????????? ??????
                if (!flag_id) {
                    Toast.makeText(RegisterActivity.this, "????????????????????? ???????????????", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(!flag_pw){
                    Toast.makeText(RegisterActivity.this, "??????????????? ???????????? ????????????", Toast.LENGTH_SHORT).show();
                    et_checkpw.requestFocus();
                    return;
                }
                if (et_id.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }

                if (et_password.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "??????????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_password.requestFocus();
                    return;
                }
                if (et_checkpw.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "???????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_checkpw.requestFocus();
                    return;
                }
                if (et_name.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if (et_email.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }

                retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<LoginModel> call2 = retrofitService.getRegister(
                        et_id.getText().toString(),
                        et_password.getText().toString(),
                        et_name.getText().toString(),
                        et_email.getText().toString(),
                        0
                );

                call2.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("?????? ??????", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
                            String code = loginModel.getCode();
                            if (code.equals("200")) {
                                Toast.makeText(RegisterActivity.this, "???????????? ??????", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else if (code.equals("400")) {
                                Toast.makeText(RegisterActivity.this, "??????????????? ??????????????????."
                                        , Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(RegisterActivity.this, "????????? ????????? ??????????????????"
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
        });
    }

}