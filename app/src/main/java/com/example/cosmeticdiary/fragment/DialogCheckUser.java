package com.example.cosmeticdiary.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cosmeticdiary.R;

import java.util.Objects;

public class DialogCheckUser extends DialogFragment {
    private static final String TAG = "CheckUserDialog";

    private TextView tvOk;

    private View.OnClickListener dialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_check_user, container, false);

        Dialog dialog = getDialog();

        tvOk = v.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(dialogListener);

        return v;
    }

    public DialogCheckUser(@NonNull Context context, View.OnClickListener dialogListener) {
        super.onAttach(context);
        this.dialogListener = dialogListener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}