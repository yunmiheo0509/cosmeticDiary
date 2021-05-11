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
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final RetrofitService[] retrofitService = new RetrofitService[2];

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
                // 중복확인
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                Log.d("중복확인", "중복확인 버튼클릭");
                Call<LoginModel> call = retrofitService[0].getDoubleCheck(et_id.getText().toString());
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
//                            Log.v("success", loginModel.getSuccess());
                            String code = loginModel.getCode();
                            if (code.equals("100")) {
                                Toast.makeText(RegisterActivity.this, "아이디가 중복됩니다.".toString(), Toast.LENGTH_SHORT).show();
                                tv_idCheckMsg.setText("아이디가 중복됩니다");
                            } else if (code.equals("300")) {
                                tv_idCheckMsg.setText("사용가능한 아이디입니다.");
                                tv_idCheckMsg.setTextColor(Color.BLUE);
                                flag_id = true;
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(RegisterActivity.this, "인터넷 연결을 확인해주세요"
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
                if (s.toString().equals(pw1)) {
                    tv_passwordCheckMsg.setText("일치합니다");
                    tv_passwordCheckMsg.setTextColor(Color.BLUE);
                    flag_pw = true;
                } else {
                    tv_passwordCheckMsg.setText("비밀번호 확인이 필요합니다");
                    tv_passwordCheckMsg.setTextColor(Color.RED);
                    flag_pw = false;
                    Log.d("pw1", pw1);
                    Log.d("pw2", s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리
                if (!flag_id) {
                    Toast.makeText(RegisterActivity.this, "중복확인버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(!flag_pw){
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    et_checkpw.requestFocus();
                    return;
                }
                if (et_id.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }

                if (et_password.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_password.requestFocus();
                    return;
                }
                if (et_checkpw.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_checkpw.requestFocus();
                    return;
                }
                if (et_name.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if (et_email.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }

                retrofitService[1] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<LoginModel> call2 = retrofitService[1].getRegister(
                        et_id.getText().toString(),
                        et_password.getText().toString(),
                        et_name.getText().toString(),
                        et_email.getText().toString()
                );
                call2.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
                            String code = loginModel.getCode();
                            if (code.equals("200")) {
                                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else if (code.equals("400")) {
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다."
                                        , Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(RegisterActivity.this, "인터넷 연결을 확인해주세요"
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