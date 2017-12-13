package com.thuong.tu.chatapplication.yolo.frontend.activities.friends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.activities.chat.MainChatActivity;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.LoginActivity;

public class AddFriendActivity extends AppCompatActivity {

    EditText input;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        initElement();
        initButton();
    }

    private void initButton() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = input.getText().toString();
                if(!phone.equals(Server.owner.get_Phone()) && !phone.isEmpty()){
                    if(Server.owner.getSingleInvitaion(phone) == null){
                        C_Friend.add_friend(input.getText().toString());
                        input.setText("");
                        Toast.makeText(AddFriendActivity.this, "Send invitation to " + phone, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AddFriendActivity.this, "Already have this invitation", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddFriendActivity.this, "This your phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initElement() {
        input = (EditText) findViewById(R.id.input_name);
        add = (Button) findViewById(R.id.add_friend);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
