package com.example.cosmeticdiary.dialog;

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

public class DialogSendEmail extends DialogFragment {
    private static final String TAG = "SendEmailDialog";

    private TextView tvOk;

    private View.OnClickListener dialogListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_send_email, container, false);

        Dialog dialog = getDialog();

        tvOk = v.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(dialogListener);

        return v;
    }

    public DialogSendEmail(@NonNull Context context, View.OnClickListener dialogListener) {
        super.onAttach(context);
        this.dialogListener = dialogListener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
