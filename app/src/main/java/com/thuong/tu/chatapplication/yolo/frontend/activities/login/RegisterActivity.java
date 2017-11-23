package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

public class RegisterActivity extends UltisActivity {
    EditText pw;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initElement();
        initButton();
    }

    private void initButton() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pw.getText().toString().equals("")) {
                    String password = pw.getText().toString();
                    C_Register.sendPass(password);
                }
            }
        });
    }

    private void initElement() {
        pw = (EditText) findViewById(R.id.edit_password);
        register = (Button) findViewById(R.id.btn_sign_in);
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
    public void onRegisterResult(C_Register.OnResultRegister result){
        if(result.getType() == C_Register.OnResultRegister.Type.register){
            if(result.isResult()){
                Server.getSocket().close();
                C_Register.OnDestroy();
                Intent i  = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
            else{

            }
        }
    }
}
