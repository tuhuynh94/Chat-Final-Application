package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;

public class PhoneNumberActivity extends UltisActivity {
    EditText number_phone;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        next = (Button) findViewById(R.id.btn_next);
        number_phone = (EditText) findViewById(R.id.edit_phone_number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number_phone.getText().equals("")) {
                    String phone = number_phone.getText().toString();
                    if(phone.charAt(0) == '0'){
                        phone = phone.substring(1);
                    }
                    C_Register.getVerifyCode(phone);
                    Intent i = new Intent(PhoneNumberActivity.this, CodeVerificationActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
