package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.API.Login;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.MainActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.MainChatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginActivity extends UltisActivity {
    Button login;
    EditText phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initElement();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.signIn(phone.getText().toString(), password.getText().toString());
            }
        });

    }
    void initElement(){
        login = (Button) findViewById(R.id.btn_sign_in);
        phone = (EditText) findViewById(R.id.edit_phone_number);
        password = (EditText) findViewById(R.id.edit_password);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void OnSuccessLogin(Login.OnLoginResult loginResult){
        if(loginResult.getCheck()){
            Server.getSocket().connect();
            Intent i = new Intent(LoginActivity.this, MainChatActivity.class);
            startActivity(i);
        }
        else{
            Log.d("login", loginResult.getText());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
