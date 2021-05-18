package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosmeticdiary.DialogCheckIdPw;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private DialogCheckIdPw dialogCheckIdPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final RetrofitService[] retrofitService = new RetrofitService[1];

        Button btnlogin = findViewById(R.id.btn_login);
        Button btnregist = findViewById(R.id.btn_resgist);
        TextView btnfindIdPw = findViewById(R.id.tv_findID);

        final EditText et_id = findViewById(R.id.et_id);
        final EditText et_password = findViewById(R.id.et_pw);

        btnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnfindIdPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindIdPwActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<LoginModel> call = retrofitService[0].getLoginCheck(et_id.getText().toString(),
                        et_password.getText().toString());

                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
                            Log.v("success", loginModel.getSuccess());
                            if (loginModel.getCode().equals("200")) {
                                Toast.makeText(LoginActivity.this, "로그인 되었습니다.".toString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

//                                appData.setPREF_LOGIN_ID(loginModel.getUserID());
//                                appData.setPREF_LOGIN("y");
                            } else {
//                                Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인해주세요"
//                                        , Toast.LENGTH_SHORT).show();
                                dialogCheckIdPw = new DialogCheckIdPw(LoginActivity.this, dialogListener);
                                dialogCheckIdPw.show();
                                et_password.setText("");
                                Log.d("ssss", response.message());
                            }



                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "인터넷 연결을 확인해주세요"
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

    //다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "확인버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            dialogCheckIdPw.dismiss();
        }
    };
}