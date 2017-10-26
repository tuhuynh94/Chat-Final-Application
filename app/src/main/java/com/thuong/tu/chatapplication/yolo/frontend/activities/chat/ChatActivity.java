package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.frontend.controllers.c_Chat;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListMessageAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final EditText edit = (EditText) findViewById(R.id.editText);
        Button btn_send = (Button) findViewById(R.id.button);
        Button btn_receive = (Button) findViewById(R.id.button1);

        ListView list = (ListView) findViewById(R.id.list_messages);
        final ArrayList<MessageModel> messages = new ArrayList<>();
        final ListMessageAdapter adapter = new ListMessageAdapter(this, R.layout.messages_receive_template, messages);
        list.setAdapter(adapter);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageModel message = new MessageModel();
                message.set_message(edit.getText().toString());
                message.set_is_send(1);
                c_Chat.creteaMessages(messages, adapter, message);
            }
        });
        btn_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageModel message = new MessageModel();
                message.set_message(edit.getText().toString());
                message.set_is_send(0);
                c_Chat.creteaMessages(messages, adapter, message);
            }
        });
    }
}
