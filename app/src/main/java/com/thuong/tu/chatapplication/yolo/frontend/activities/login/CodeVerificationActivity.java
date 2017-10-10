package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONObject;

import java.util.HashMap;

public class CodeVerificationActivity extends AppCompatActivity {
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
                    Server.getSocket().connect();
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("code", verify_code.getText().toString());
                    JSONObject json = new JSONObject(data);
                    Server.getSocket().emit("respose", json);
//                    Server.getSocket().disconnect();
                    Intent i = new Intent(CodeVerificationActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
