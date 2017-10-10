package com.thuong.tu.chatapplication.yolo.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.LoginActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.PhoneNumberActivity;

public class MainActivity extends AppCompatActivity {
    Button sign_in, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_in = (Button) findViewById(R.id.btn_sign_in);
        register = (Button) findViewById(R.id.btn_register);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PhoneNumberActivity.class);
                startActivity(i);
            }
        });
    }
}
