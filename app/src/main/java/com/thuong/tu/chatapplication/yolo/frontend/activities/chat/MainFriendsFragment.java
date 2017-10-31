package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.frontend.utils.ChildPagerAdapter;
import com.thuong.tu.chatapplication.yolo.frontend.utils.PagerAdapter;

public class MainFriendsFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    private static MainFriendsFragment fragment;

    public MainFriendsFragment() {
        // Required empty public constructor
    }

    public static MainFriendsFragment getInstance() {
        if(fragment == null){
            fragment = new MainFriendsFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_friends, container, false);
        initPager(view);
        // Inflate the layout for this fragment
        return view;
    }
    private void initPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        FragmentManager manager = getChildFragmentManager();
        ChildPagerAdapter adapter = new ChildPagerAdapter(manager);
        adapter.addFragment(ContactsFragment.getInstance(), "");
        adapter.addFragment(FriendsFragment.getInstance(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.contact);
        tabLayout.getTabAt(1).setIcon(R.drawable.friends);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
