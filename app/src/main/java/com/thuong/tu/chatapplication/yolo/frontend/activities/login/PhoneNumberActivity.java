package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PhoneNumberActivity extends UltisActivity {
    EditText number_phone;
    Button next;
    Context  context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        next = (Button) findViewById(R.id.btn_next);
        number_phone = (EditText) findViewById(R.id.edit_phone_number);
        context = PhoneNumberActivity.this;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!number_phone.getText().equals("")) {
                    String phone = number_phone.getText().toString();
                    if(phone.charAt(0) == '0'){
                        phone = phone.substring(1);
                    }
                    C_Register.getVerifyCode(phone);
                }
            }
        });
    }
    public void toastMessage(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnResult(C_Register.OnResultRegister result){
        if(result.isResult()){
            Intent i = new Intent(PhoneNumberActivity.this, CodeVerificationActivity.class);
            startActivity(i);
        }
        else{
            toastMessage(result.getText());
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneNumberActivity.this);
        builder.setTitle("Are you want to go back main menu?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(PhoneNumberActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
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
