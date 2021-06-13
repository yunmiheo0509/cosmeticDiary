package com.example.cosmeticdiary.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.CursorLoader;

import com.example.cosmeticdiary.dialog.DialogCheckDelete;
import com.example.cosmeticdiary.util.MySharedPreferences;
import com.example.cosmeticdiary.R;
import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.util.retrofit.RetrofitHelper;
import com.example.cosmeticdiary.util.retrofit.RetrofitService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends AppCompatActivity {
    DialogCheckDelete dialogCheckDelete;
    TextView tv_ingredient;
    EditText et_name, et_write;
    RadioGroup radioGroup;
    ImageView iv_writephoto;
    RetrofitService retrofitService;
    String imageBase64 = null;
    Button btn_cancel, btn_right, btn_search, btn_edit;
    CheckBox chkJopssal, chkDry, chkHwanongsung, chkGood, chkTrouble, chkEtc;
    ScrollView scrollView;
    ConstraintLayout constraintLayout;
    String date;

    String dateMain, dateDB, cosmeticNameDB, content;

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

        final Intent intent = getIntent();
        int intentValue = intent.getIntExtra("writing", 0);
        dateMain = intent.getStringExtra("dateMain");

        // 글 작성
        switch (intentValue) {
            case 1000:
                btn_edit.setVisibility(View.GONE);
                btn_right.setText("저장");
                btn_search.setVisibility(View.VISIBLE);

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

                        Call<LoginModel> call = retrofitService.getWriting(id, cosmetic, imageBase64, satisfy, content, dateMain,
                                ingredient, jopssal, dry, hwanongsung, good, trouble, etc);
                        call.enqueue(new Callback<LoginModel>() {
                            @Override
                            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                if (response.isSuccessful()) {
                                    Log.d("연결 성공", response.message());
                                    LoginModel loginModel = response.body();
                                    Log.v("code", loginModel.getCode());
                                    System.out.println(loginModel.getCode() + loginModel.getSuccess());
                                    Toast.makeText(WritingActivity.this, "등록 완료", Toast.LENGTH_SHORT).show();
                                    if (loginModel.getCode().equals("200")) {
                                        Log.v("code", loginModel.getCode());
                                        System.out.println("success");
                                    } else {
                                        Log.d("ssss", response.message());
                                    }
                                    finish();
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
                    }
                });
                break;
            default:
                btn_edit.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.GONE);

                for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                    View child = constraintLayout.getChildAt(i);
                    child.setEnabled(false);
                }

                btn_right.setText("삭제");
                cosmeticNameDB = intent.getStringExtra("cosmeticname");
                et_name.setText(cosmeticNameDB);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            URL url = new URL(intent.getStringExtra("img"));
                            Log.d("url주소", url.toString());
                            URLConnection conn = url.openConnection();
                            conn.connect();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            Bitmap bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            iv_writephoto.setImageBitmap(bm);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
                switch (intent.getStringExtra("satisfy")) {
                    case "상":
                        radioGroup.check(R.id.radiobtn_good);
                        break;
                    case "중":
                        radioGroup.check(R.id.radiobtn_mid);
                        break;
                    case "하":
                        radioGroup.check(R.id.radiobtn_bad);
                        break;
                }
                et_write.setText(intent.getStringExtra("content"));
                tv_ingredient.setText(intent.getStringExtra("ingredient"));
                dateDB = intent.getStringExtra("date");
                content = intent.getStringExtra("content");
                et_write.setText(content);
                tv_ingredient.setText(intent.getStringExtra("ingredient"));
                setCheckbox(chkJopssal, intent.getStringExtra("jopssal"));
                setCheckbox(chkDry, intent.getStringExtra("dry"));
                setCheckbox(chkHwanongsung, intent.getStringExtra("hwanongsung"));
                setCheckbox(chkGood, intent.getStringExtra("good"));
                setCheckbox(chkTrouble, intent.getStringExtra("trouble"));
                setCheckbox(chkEtc, intent.getStringExtra("etc"));

                // 수정
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
                            View child = constraintLayout.getChildAt(i);
                            child.setEnabled(true);
                        }

                        btn_edit.setVisibility(View.GONE);
                        btn_search.setVisibility(View.VISIBLE);
                        btn_right.setText("저장");

                        // 취소 처리
                        btn_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        // 수정 후 저장
                        btn_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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

                                Call<LoginModel> call = retrofitService.EditWriting(id, cosmetic, imageBase64, satisfy, content, dateDB,
                                        ingredient, jopssal, dry, hwanongsung, good, trouble, etc, cosmeticNameDB);
                                call.enqueue(new Callback<LoginModel>() {
                                    @Override
                                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                        if (response.isSuccessful()) {
                                            Log.d("연결 성공", response.message());
                                            LoginModel loginModel = response.body();
                                            Log.v("code", loginModel.getCode());
                                            Toast.makeText(WritingActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
                                            if (loginModel.getCode().equals("200")) {
                                                Log.v("code", loginModel.getCode());
                                            } else {
                                                Log.d("ssss", response.message());
                                            }
                                            finish();
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
                            }
                        });
                    }
                });

                // 취소 처리
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
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

                break;
        }

        // 검색 처리
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WritingActivity.this, SearchCosmeticActivity.class);
                startActivityForResult(intent, 1);
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
                case 1:
                    et_name.setText(data.getStringExtra("name"));
                    tv_ingredient.setText(data.getStringExtra("ingredient"));
                    break;
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
//
//                        ExifInterface exif = null;
//                        try {
//                            exif = new ExifInterface(in); // path 파일 uri
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//                        Bitmap bmRotated = rotateBitmap(resized, orientation);

                        iv_writephoto.setImageBitmap(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
//            case ExifInterface.ORIENTATION_NORMAL:
//                return bitmap;
//            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
//                matrix.setScale(-1, 1);
//                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
//            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
//                matrix.setRotate(180);
//                matrix.postScale(-1, 1);
//                break;
//            case ExifInterface.ORIENTATION_TRANSPOSE:
//                matrix.setRotate(90);
//                matrix.postScale(-1, 1);
//                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
//            case ExifInterface.ORIENTATION_TRANSVERSE:
//                matrix.setRotate(-90);
//                matrix.postScale(-1, 1);
//                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    //삭제 다이얼로그창
    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_ok:
                    // 글 내용 db에서 삭제
                    Log.d("다이얼로그뜸", "tv_ok선택함");
                    String id = MySharedPreferences.getUserId(WritingActivity.this);
                    Log.d("다이얼로그뜸", id + content + dateDB + cosmeticNameDB);
                    retrofitService = RetrofitHelper.getRetrofit().create(RetrofitService.class);
                    Call<LoginModel> call = retrofitService.deleteWriting(id, content, dateDB, cosmeticNameDB);
                    call.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            if (response.isSuccessful()) {
                                Log.d("연결 성공", response.message());
                                LoginModel loginModel = response.body();
                                Log.v("code", loginModel.getCode());
                                if (loginModel.getCode().equals("200")) {
                                    Log.v("code", loginModel.getCode());
                                } else {
                                    Log.d("ssss", response.message());
                                }
                                Toast.makeText(WritingActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
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
                case R.id.tv_cancel:
                    dialogCheckDelete.dismiss();
            }
        }
    };

    private void setCheckbox(CheckBox chkbox, String bool) {
        if (bool.equals("true")) {
            chkbox.setChecked(true);
        } else chkbox.setChecked(false);
    }
}