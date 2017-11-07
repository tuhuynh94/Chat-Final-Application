package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CodeVerificationActivity extends UltisActivity {
    EditText verify_code;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        verify_code = (EditText) findViewById(R.id.edit_phone_number);
        next = (Button) findViewById(R.id.btn_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(!verify_code.getText().equals("")){
                    C_Register.onCreate();
                    C_Register.sendVerifyCode(verify_code.getText().toString());
                }
            }
        });
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
        if(result.getType() == C_Register.OnResultRegister.Type.code) {
            if(result.isResult()){
                Intent i  = new Intent(CodeVerificationActivity.this, RegisterActivity.class);
                startActivity(i);
            }
            else{

            }
        }
    }
}

