package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Message;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.UltisActivity;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListMessageAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ChatActivity extends UltisActivity {
    ArrayList<MessageModel> messages;
    Button back,send, option;
    EditText input_message;
    TextView name;
    ConversationModel conversationModel;
    ListMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ListView list = (ListView) findViewById(R.id.list_messages);
        Intent intent = getIntent();
        conversationModel = (ConversationModel) intent.getSerializableExtra("conversation");
        if(conversationModel != null){
            messages = Server.owner.get_AllMessageByConversationID(conversationModel.getConversation_id());
        }
        else{
            messages = new ArrayList<>();
        }
        adapter = new ListMessageAdapter(this, R.layout.messages_receive_template, messages);
        list.setAdapter(adapter);
        initElements();
        name.setText(conversationModel.getConversation_name());
        assignButton();
    }

    private void assignButton() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Message.addMessage(input_message.getText().toString(), conversationModel.getConversation_id());
            }
        });
    }

    private void initElements() {
        back = (Button) findViewById(R.id.back);
        send = (Button) findViewById(R.id.send);
        input_message = (EditText) findViewById(R.id.input_mess);
        name = (TextView) findViewById(R.id.txt_name);
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

    @Subscribe
    public  void OnMess(C_Message.OnMess onMess){
        messages = Server.owner.get_AllMessageByConversationID(conversationModel.getConversation_id());
        adapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void OnInviteFriend(C_Friend.OnResultFriend onResultFriend){
        if(onResultFriend.getType() == C_Friend.OnResultFriend.Type.ADD_FRIEND){
            Toast.makeText(getApplicationContext(), "Accept", Toast.LENGTH_SHORT).show();
        }
        if(onResultFriend.getType() == C_Friend.OnResultFriend.Type.DENY_ADD_FRIEND){
            Toast.makeText(getApplicationContext(), "Deny", Toast.LENGTH_SHORT).show();
        }
    }
}
