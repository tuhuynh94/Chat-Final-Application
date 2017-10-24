package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.frontend.utils.PagerAdapter;

public class MainChatActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    int[] icon = new int[]{
      R.drawable.chat_icon,
      R.drawable.groups,
      R.drawable.contacts
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        initPager();
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
