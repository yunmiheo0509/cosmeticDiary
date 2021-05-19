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

public class FindIdFragment extends Fragment {
    View v;
    Button btn_findId;

    DialogCheckUser dialogCheckUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_find_id, container, false);
        btn_findId = v.findViewById(R.id.btn_findId);

        btn_findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCheckUser = new DialogCheckUser(getContext(), dialogListener);
                dialogCheckUser.show(getFragmentManager(),"CheckUserDialog");
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
        }
    };
}