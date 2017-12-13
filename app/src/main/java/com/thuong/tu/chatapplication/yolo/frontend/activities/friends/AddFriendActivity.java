package com.thuong.tu.chatapplication.yolo.frontend.activities.friends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

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
}
