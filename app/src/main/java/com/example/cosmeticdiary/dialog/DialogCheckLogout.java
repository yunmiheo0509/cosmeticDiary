package com.example.cosmeticdiary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cosmeticdiary.R;

public class DialogCheckLogout extends Dialog {
    private TextView tvOk;
    private TextView tvCancel;

    private View.OnClickListener dialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_logout);

        tvCancel = findViewById(R.id.tv_cancel);
        tvOk = findViewById(R.id.tv_ok);

        tvCancel.setOnClickListener(dialogListener);
        tvOk.setOnClickListener(dialogListener);
    }

    public DialogCheckLogout(@NonNull Context context, View.OnClickListener dialogListener) {
        super(context);
        this.dialogListener = dialogListener;
    }
}
