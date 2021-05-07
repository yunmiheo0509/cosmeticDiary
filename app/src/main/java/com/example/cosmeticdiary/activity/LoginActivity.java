package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final RetrofitService[] retrofitService = new RetrofitService[1];

        Button btnlogin = findViewById(R.id.btn_login);
        Button btnregist = findViewById(R.id.btn_resgist);
        TextView btnfindIdPw = findViewById(R.id.tv_findID);

        final EditText et_id = findViewById(R.id.et_id);
        final EditText et_password = findViewById(R.id.et_newpw);

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
                                Log.d("성공", response.message());

                                LoginModel loginModel = response.body();

                                Log.v("code", loginModel.getUserID());
                                Log.v("success", loginModel.getLoginBy());

                                Toast.makeText(LoginActivity.this, loginModel.getUserID().toString(), Toast.LENGTH_SHORT).show();

//                                appData.setPREF_LOGIN_ID(loginModel.getUserID());
//                                appData.setPREF_LOGIN("y");

//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(intent);
                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인해주세요"
                                    , Toast.LENGTH_SHORT).show();
                            et_password.setText("");
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