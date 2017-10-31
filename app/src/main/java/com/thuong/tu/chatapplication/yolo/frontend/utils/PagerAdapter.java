package com.thuong.tu.chatapplication.yolo.frontend.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter{
    private static List<Fragment> listFragment = new ArrayList<>();
    private static List<String> listFragmentTitle = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public static void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listFragmentTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  listFragmentTitle.get(position);
    }
}
