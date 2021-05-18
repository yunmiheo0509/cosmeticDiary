package com.example.cosmeticdiary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogCheckIdPw extends Dialog {
    private TextView tvOk;

    private View.OnClickListener dialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkidpw);

        //배경 흐리게 효과
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        layoutParams.dimAmount = 0.8f;
//        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_checkidpw);

        //셋팅
        tvOk = findViewById(R.id.tv_ok);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        tvOk.setOnClickListener(dialogListener);
    }

    public DialogCheckIdPw(@NonNull Context context, View.OnClickListener dialogListener) {
        super(context);
        this.dialogListener = dialogListener;
    }
}
