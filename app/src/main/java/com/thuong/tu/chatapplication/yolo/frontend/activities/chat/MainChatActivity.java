package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_User;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.activities.friends.AddFriendActivity;
import com.thuong.tu.chatapplication.yolo.frontend.utils.PagerAdapter;
import com.thuong.tu.chatapplication.yolo.utils.FileController;
import com.thuong.tu.chatapplication.yolo.utils.GalleryManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQUEST_TAKE_PHOTO = 104;
    private final int REQUEST_CHOOSE_PHOTO = 401;
    ViewPager viewPager;
    TabLayout tabLayout;
    Calendar myCalendar = Calendar.getInstance();
    de.hdodenhof.circleimageview.CircleImageView avatar_edit_user;
    String image_source;

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
        View headlayout = navigationView.getHeaderView(0);
        CircleImageView avatar_mine = (CircleImageView) headlayout.findViewById(R.id.avatar_mine);
        if (!Server.owner.get_imageSource().isEmpty()) {
            Picasso.with(this).load(Server.owner.get_imageSource()).into(avatar_mine);
        }
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        PagerAdapter.addFragment(RecentsFragment.getInstance(), "");
        PagerAdapter.addFragment(MainFriendsFragment.getInstance(), "");
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
            EditText email, phone, username;
            Spinner gender = (Spinner) diaglogView.findViewById(R.id.gender);
            email = (EditText) diaglogView.findViewById(R.id.email);
            phone = (EditText) diaglogView.findViewById(R.id.phone);
            username = (EditText) diaglogView.findViewById(R.id.username);
            avatar_edit_user = (CircleImageView) diaglogView.findViewById(R.id.avatar);

            username.setText(Server.owner.get_username().equals(null) ? "" : Server.owner.get_username());
            email.setText(Server.owner.get_Email().equals(null) ? "" : Server.owner.get_Email());
            phone.setText(Server.owner.get_Phone().equals(null) ? "" : Server.owner.get_Phone());

            image_source = (Server.owner.get_imageSource() != null && !Server.owner.get_imageSource().isEmpty()) ? Server.owner.get_imageSource() : "";
            if (!image_source.isEmpty()) {
                Picasso.with(this).load(image_source).into(avatar_edit_user);
            }
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

            avatar_edit_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GalleryManager.choosePicture(MainChatActivity.this, REQUEST_CHOOSE_PHOTO);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username_update = username.getText().toString();
                    String email_update = email.getText().toString();
                    String phone_update = phone.getText().toString();
                    boolean gender_update = gender.getSelectedItem().toString().equals("MALE");
                    Date birthday = myCalendar.getTime();
                    String image_source_update = image_source;
                    C_User.updateUser(gender_update, phone_update, username_update, birthday, email_update, image_source_update);
                }
            });

            builder.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private DatePickerDialog.OnDateSetListener datePicker(Button button) {
        myCalendar = Calendar.getInstance();
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
        String myFormat = "MMM dd, yyyy"; //In which you need put here
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultUser(C_User.OnResultUser onResultUser) {
        if (onResultUser.getType() == C_User.OnResultUser.Type.CHANGE_AVATAR) {
            avatar_edit_user.setImageBitmap(onResultUser.getBitmap());
            image_source = onResultUser.getPath();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Server.beforDisconnet();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            byte[] bytes = FileController.getByteArrayFromBitmap(bitmap);
            Server.getSocket().emit("change_avatar", bytes);
        }
        else if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK){
            try{
                Uri imageURI = data.getData();
                InputStream is = getContentResolver().openInputStream(imageURI);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                byte[] bytes = FileController.getByteArrayFromBitmap(bitmap);
                Server.getSocket().emit("change_avatar", bytes);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
