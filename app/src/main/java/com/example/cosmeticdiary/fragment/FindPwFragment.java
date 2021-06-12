package com.example.cosmeticdiary.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosmeticdiary.GmailSender;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.retrofit.RetrofitService;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwFragment extends Fragment {
    View v;
    Button btn_findPw;
    DialogSendEmail dialogSendEmail;
    DialogCheckUser dialogCheckUser;
    String randomPw;
    EditText et_id, et_email;
    boolean check;
    RetrofitService retrofitService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_pw, container, false);

        et_id = v.findViewById(R.id.et_id);
        et_email = v.findViewById(R.id.et_email);
        btn_findPw = v.findViewById(R.id.btn_findPw);

        //이메일전송
        btn_findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GmailSender gMailSender = new GmailSender("cwd9447@gmail.com", "backupchl6372");
                randomPw = getRandomPassword(10);
                changePw(et_id.getText().toString(), randomPw, et_email.getText().toString());
                if(check) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        public Void doInBackground(Void... voids) {
                            try {
                                //GMailSender.sendMail(제목, 본문내용, 받는사람);

                                gMailSender.sendMail("[CosmeticDiary] 새로운 비밀번호",
                                        "당신의 새로운 비밀번호 입니다. 로그인 후 꼭 비밀번호를 변경해주세요.\n" + randomPw,
                                        et_email.getText().toString());
                                dialogSendEmail = new DialogSendEmail(getContext(), dialogListener);
                                dialogSendEmail.show(getFragmentManager(), "SendEmailDialog");


//                    sendResultOk = true;
//                    button.setEnabled(false);
                            } catch (SendFailedException e) {
                                Toast.makeText(getContext(), "이메일 형식이 잘못되었습니다", Toast.LENGTH_SHORT).show();
                            } catch (MessagingException e) {
                                Toast.makeText(getContext(), "인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e("이메일 보내기", e.getMessage(), e);
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    public static String getRandomPassword(int length) {
        String[] passwords = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        StringBuilder builder = new StringBuilder("");
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(passwords[random.nextInt(passwords.length)]);
        }
        return builder.toString();
    }

    public void changePw(String id, String newPw, String email) {
        retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);

        Call<LoginModel> call = retrofitService.getFindPw(id, newPw, email);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공", response.message());
                    LoginModel loginModel = response.body();
                    System.out.println(response.body().toString());
                    if (loginModel.getCode().equals("206")) {
                        check =true;
                        Log.v("code코드 ", loginModel.getCode()+check);
//                        Log.v("success", loginModel.getSuccess());
                        dialogCheckUser = new DialogCheckUser(getContext(), dialogListener2,"회원정보 존재하지 않음");
                        dialogCheckUser.show(getFragmentManager(), "CheckUserDialog");
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
    //다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogSendEmail.dismiss();
        }
    };
    private View.OnClickListener dialogListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogCheckUser.dismiss();
        }
    };

}