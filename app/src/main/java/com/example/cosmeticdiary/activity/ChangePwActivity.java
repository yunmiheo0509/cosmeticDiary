package com.example.cosmeticdiary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cosmeticdiary.util.MySharedPreferences;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity {
    Button btn_complete;
    ImageView iv_backbtn;
    EditText et_currentpw, et_newpw, et_checknewpw;

    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        btn_complete = findViewById(R.id.btn_complete);
        iv_backbtn = findViewById(R.id.iv_back);
        et_currentpw = findViewById(R.id.et_currentpw);
        et_newpw = findViewById(R.id.et_newpw);
        et_checknewpw = findViewById(R.id.et_checknewpw);

        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String userid = MySharedPreferences.getUserId(ChangePwActivity.this);
        final String userpass = MySharedPreferences.getUserPass(ChangePwActivity.this);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userpass.equals(et_currentpw.getText().toString())) {
                    //현재 비밀번호가 일치하지 않습니다.
                    Toast.makeText(ChangePwActivity.this, "현재 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else if (!et_newpw.getText().toString().equals(et_checknewpw.getText().toString())){
                    //새비밀번호가 일치하지 않습니다.
                    Toast.makeText(ChangePwActivity.this, "새비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    changePw(userid, et_newpw.getText().toString());
                }
            }
        });
    }

    public void changePw(String id, final String newPw) {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<LoginModel> call = retrofitService.getChangePw(id, newPw);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공", response.message());
                    LoginModel loginModel = response.body();
                    System.out.println(response.body().toString());
                    if (loginModel.getCode().equals("200")) {
//                        Log.v("code", loginModel.getCode());
//                        Log.v("success", loginModel.getSuccess());
                        Toast.makeText(ChangePwActivity.this, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();
                        MySharedPreferences.setUserPass(getApplicationContext(), newPw);
                        finish();
                    } else {
                        Log.d("ssss", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("ssss", t.getMessage());
            }
        });
    }
}