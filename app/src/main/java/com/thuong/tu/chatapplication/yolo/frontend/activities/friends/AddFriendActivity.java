package com.thuong.tu.chatapplication.yolo.frontend.activities.friends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;

public class AddFriendActivity extends AppCompatActivity {

    EditText input;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initElement();
        initButton();
    }

    private void initButton() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Friend.add_friend(input.getText().toString());
            }
        });
    }

    private void initElement() {
        input = (EditText) findViewById(R.id.input_name);
        add = (Button) findViewById(R.id.add_friend);
    }
}
