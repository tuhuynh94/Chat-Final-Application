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
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.controllers.c_Chat;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListMessageAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatActivity extends UltisActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ListView list = (ListView) findViewById(R.id.list_messages);
        final ArrayList<MessageModel> messages = new ArrayList<>();
        final ListMessageAdapter adapter = new ListMessageAdapter(this, R.layout.messages_receive_template, messages);
        list.setAdapter(adapter);
    }
}
