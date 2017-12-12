package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.frontend.utils.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.thuong.tu.chatapplication.yolo.frontend.utils.ProcessDialogHelper.createProcessDialog;

public class CodeVerificationActivity extends UltisActivity {
    EditText verify_code;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
    ProgressDialog progressDialog;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        verify_code = (EditText) findViewById(R.id.edit_phone_number);
        next = (Button) findViewById(R.id.btn_next);
        progressDialog = createProcessDialog(CodeVerificationActivity.this, "Sending code...");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(!verify_code.getText().equals("")){
                    progressDialog.show();
                    next.setEnabled(false);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterResult(C_Register.OnResultRegister result){
        if(result.getType() == C_Register.OnResultRegister.Type.code) {
            if(result.isResult()){
                progressDialog.dismiss();
                next.setEnabled(true);
                Intent i  = new Intent(CodeVerificationActivity.this, RegisterActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(CodeVerificationActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                next.setEnabled(true);
            }
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Are you want to go back main menu?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(CodeVerificationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}

