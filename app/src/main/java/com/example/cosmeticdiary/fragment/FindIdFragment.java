package com.example.cosmeticdiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.activity.LoginActivity;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindIdFragment extends Fragment {
    View v;
    Button btn_findId;
    EditText et_name, et_email;
    DialogCheckUser dialogCheckUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RetrofitService[] retrofitService = new RetrofitService[1];
        v = inflater.inflate(R.layout.fragment_find_id, container, false);
        et_name = v.findViewById(R.id.et_name);
        et_email = v.findViewById(R.id.et_email);
        btn_findId = v.findViewById(R.id.btn_findId);

        btn_findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitService[0] = RetrofitHelper.getRetrofit().create(RetrofitService.class);

                Call<LoginModel> call = retrofitService[0].getFindId(et_name.getText().toString(),
                        et_email.getText().toString());

                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        System.out.println("서버연결시도");
                        if (response.isSuccessful()) {
                            Log.d("연결 성공", response.message());
                            LoginModel loginModel = response.body();
                            Log.v("code", loginModel.getCode());
                            Log.v("success", loginModel.getSuccess());
                            System.out.println(response.body().toString());
                            if (loginModel.getCode().equals("200")) {
                                dialogCheckUser = new DialogCheckUser(getContext(), dialogListener,"회원아이디: "+loginModel.getSuccess());
                                dialogCheckUser.show(getFragmentManager(), "CheckUserDialog");
                            } else {
                                dialogCheckUser = new DialogCheckUser(getContext(), dialogListener2,"회원정보 존재하지 않음");
                                dialogCheckUser.show(getFragmentManager(), "CheckUserDialog");
                                Log.d("ssss", response.message());
                            }

                        } else if (response.code() == 404) {
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

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogCheckUser.dismiss();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener dialogListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogCheckUser.dismiss();
        }
    };
}