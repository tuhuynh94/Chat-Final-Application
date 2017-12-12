package com.thuong.tu.chatapplication.yolo.frontend.activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.utils.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.LoginActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.PhoneNumberActivity;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends UltisActivity implements Observer {
    Button sign_in, register;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Server.getSocket().connect();
        sign_in = (Button) findViewById(R.id.btn_sign_in);
        register = (Button) findViewById(R.id.btn_register);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                Server.getSocket().connect();
                C_Register.onCreate();
                Intent i = new Intent(MainActivity.this, PhoneNumberActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void update(Observable observable,Object data) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        C_Register.OnDestroy();
        Server.getSocket().close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Server.beforDisconnet();
    }
}
