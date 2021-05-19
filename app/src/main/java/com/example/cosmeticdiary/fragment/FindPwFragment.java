package com.example.cosmeticdiary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cosmeticdiary.R;

public class FindPwFragment extends Fragment {
    View v;
    Button btn_findPw;

    DialogSendEmail dialogSendEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_pw, container, false);

        btn_findPw = v.findViewById(R.id.btn_findPw);

        btn_findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSendEmail = new DialogSendEmail(getContext(), dialogListener);
                dialogSendEmail.show(getFragmentManager(),"SendEmailDialog");
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
            dialogSendEmail.dismiss();
        }
    };
}