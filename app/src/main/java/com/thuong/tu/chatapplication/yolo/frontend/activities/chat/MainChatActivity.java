package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.MainActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.friends.AddFriendActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.PhoneNumberActivity;
import com.thuong.tu.chatapplication.yolo.frontend.utils.PagerAdapter;
import com.thuong.tu.chatapplication.yolo.utils.FileController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_temp);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        createNavigation();
        initPager();
    }

    private void createNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        adapter.addFragment(RecentsFragment.getInstance(), "");
        adapter.addFragment(MainFriendsFragment.getInstance(), "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.chat);
        tabLayout.getTabAt(1).setIcon(R.drawable.user);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainChatActivity.this);
            builder.setTitle("Are you want to quit application?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                    System.exit(0);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create();
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_chat_activity_temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_friend) {
            Intent i = new Intent(MainChatActivity.this, AddFriendActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_user) {
            // Handle the camera action
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View diaglogView = inflater.inflate(R.layout.update_user, null);
            builder.setView(diaglogView);

            Button save = (Button) diaglogView.findViewById(R.id.save);
            Button date = (Button) diaglogView.findViewById(R.id.birthday);
            de.hdodenhof.circleimageview.CircleImageView avatar;
            EditText email, phone, username;
            email = (EditText) diaglogView.findViewById(R.id.email);
            phone = (EditText) diaglogView.findViewById(R.id.phone);
            username = (EditText) diaglogView.findViewById(R.id.username);
            avatar = (CircleImageView) diaglogView.findViewById(R.id.avatar);

            username.setText(Server.owner.get_username() == null ? "" : Server.owner.get_username());
            email.setText(Server.owner.get_Email() == null ? "" : Server.owner.get_Email());
            phone.setText(Server.owner.get_Phone() == null ? "" : Server.owner.get_Phone());
            DatePickerDialog.OnDateSetListener datePicker = datePicker(date);
            Calendar myCalendar = Calendar.getInstance();
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(MainChatActivity.this, datePicker, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_temp);
                    byte[] bytes = FileController.getByteArrayFromBitmap(bitmap);

                    Server.getSocket().emit("image", bytes);
                }
            });

            builder.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private DatePickerDialog.OnDateSetListener datePicker(Button button) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(button, myCalendar);
            }

        };
        return date;
    }

    private void updateLabel(Button button, Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        button.setText(sdf.format(myCalendar.getTime()));
    }

    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResult(C_Friend.OnResultFriend onResultFriend) {
        if (onResultFriend.getType() == C_Friend.OnResultFriend.Type.ACCEPT_ADD_FRIEND) {
            Toast.makeText(getApplicationContext(), "accept", Toast.LENGTH_SHORT).show();
        }
        if (onResultFriend.getType() == C_Friend.OnResultFriend.Type.DENY_ADD_FRIEND) {
            Toast.makeText(getApplicationContext(), "deny", Toast.LENGTH_SHORT).show();
        }
    }
}
