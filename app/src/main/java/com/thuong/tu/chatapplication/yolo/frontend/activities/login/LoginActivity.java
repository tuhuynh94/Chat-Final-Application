package com.thuong.tu.chatapplication.yolo.frontend.activities.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.utils.uService;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_sign_in);
        phone = (EditText) findViewById(R.id.edit_phone_number);
        password = (EditText) findViewById(R.id.edit_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k = 0;
                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("phone", phone.getText().toString());
                builder.appendQueryParameter("pw", password.getText().toString());
                String url = uService.m_host + uService.m_sign_in_link;
                new uService.request().execute(builder, url);
            }
        });
    }
}
