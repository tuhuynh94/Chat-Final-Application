package com.thuong.tu.chatapplication.yolo.frontend.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.RecentsFragment;
import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.ContactsFragment;
import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.GroupsFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0 : frag = RecentsFragment.getInstance();
                break;
            case 1: frag = GroupsFragment.getInstance();
                break;
            case 2: frag = ContactsFragment.getInstance();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0 : title = "Chats";
                break;
            case 1: title = "Groups";
                break;
            case 2: title = "Contacts";
                break;
        }
        return  title;
    }
}
