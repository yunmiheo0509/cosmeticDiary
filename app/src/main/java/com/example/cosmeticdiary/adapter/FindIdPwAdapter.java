package com.example.cosmeticdiary.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cosmeticdiary.FindIdFragment;
import com.example.cosmeticdiary.FindPwFragment;

import java.util.ArrayList;

public class FindIdPwAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> menus = new ArrayList<>();

    public FindIdPwAdapter(@NonNull FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new FindIdFragment());
        items.add(new FindPwFragment());

        menus.add("아이디찾기");
        menus.add("비밀번호찾기");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menus.get(position);
    }
}
