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

public class RegisterActivity extends UltisActivity {
    EditText pw, confirm_pw;
    Button register;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        progressDialog = createProcessDialog(RegisterActivity.this, "Registering...");
        initElement();
        initButton();
    }

    private void initButton() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAnimation(buttonClick);
                if(pw.getText().toString().equals(confirm_pw.getText().toString())){
                    if (!pw.getText().toString().equals("")) {
                        progressDialog.show();
                        register.setEnabled(false);
                        String password = pw.getText().toString();
                        C_Register.sendPass(password);
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Confirm password must be same with password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initElement() {
        pw = (EditText) findViewById(R.id.edit_password);
        confirm_pw = (EditText) findViewById(R.id.edit_confirm_password);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterResult(C_Register.OnResultRegister result){
        if(result.getType() == C_Register.OnResultRegister.Type.register){
            if(result.isResult()){
                progressDialog.dismiss();
                register.setEnabled(true);
                Intent i  = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(RegisterActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                register.setEnabled(true);
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
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
