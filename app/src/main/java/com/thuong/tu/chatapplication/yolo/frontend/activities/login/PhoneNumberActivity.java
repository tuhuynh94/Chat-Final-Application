package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Login;

public class PhoneNumberActivity extends AppCompatActivity {
    EditText number_phone;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        C_Login.onCreate();

        next = (Button) findViewById(R.id.btn_next);
        number_phone = (EditText) findViewById(R.id.edit_phone_number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!number_phone.getText().equals("")){
                    C_Login.getVerifyCode(number_phone.getText().toString());


                    Intent i = new Intent(PhoneNumberActivity.this, CodeVerificationActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
