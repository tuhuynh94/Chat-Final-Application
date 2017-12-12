package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.API.Login;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.utils.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.activities.MainActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.MainChatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.thuong.tu.chatapplication.yolo.frontend.utils.ProcessDialogHelper.createProcessDialog;

public class LoginActivity extends UltisActivity {
    Button login;
    EditText phone, password;
    ProgressDialog progressDialog;
    boolean check_login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        progressDialog = createProcessDialog(LoginActivity.this, "Logging in....");
        initElement();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(check_login == false){
                            check_login = true;
                            Login.signIn(phone.getText().toString(), password.getText().toString());
                        }
                    }
                }, 1000);
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
        C_Register.OnDestroy();
        Server.getSocket().close();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void OnSuccessLogin(Login.OnLoginResult loginResult){
        if(loginResult.getCheck()){
            progressDialog.dismiss();
            Server.connectNode();
            Intent i = new Intent(LoginActivity.this, MainChatActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(LoginActivity.this, loginResult.getText(), Toast.LENGTH_SHORT).show();
            check_login = false;
            progressDialog.dismiss();
            Log.d("login", loginResult.getText());
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
