package com.thuong.tu.chatapplication.yolo.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.LoginActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.PhoneNumberActivity;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    Button sign_in, register;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.getSocket().connect();
        sign_in = (Button) findViewById(R.id.btn_sign_in);
        register = (Button) findViewById(R.id.btn_register);
        Server.getSocket().on("abc", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                int k = 0;
            }
        });

        Server server = Server.getInstance();
        server.addObserver(this);
        server.getData();

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
                Intent i = new Intent(MainActivity.this, PhoneNumberActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void update(Observable observable,Object data) {
    }
}
