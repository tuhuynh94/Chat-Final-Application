package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;

public class CodeVerificationActivity extends UltisActivity {
    EditText verify_code;
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
                if(!verify_code.getText().equals("")){
                    C_Register.sendVerifyCode(verify_code.getText().toString());
                    C_Register.onCreate();
                    Intent i = new Intent(CodeVerificationActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}

