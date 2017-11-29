package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
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

public class ChatActivity extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);
    ArrayList<MessageModel> messages;
    Button back,send, info;
    EditText input_message;
    TextView name;
    ConversationModel conversationModel;
    ListMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
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
                v.setAnimation(buttonClick);
                C_Message.addMessage(input_message.getText().toString(), conversationModel.getConversation_id());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAnimation(buttonClick);
                ChatActivity.super.onBackPressed();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }

    private void initElements() {
        back = (Button) findViewById(R.id.back);
        send = (Button) findViewById(R.id.send);
        input_message = (EditText) findViewById(R.id.input_mess);
        name = (TextView) findViewById(R.id.name);
        info = (Button) findViewById(R.id.info);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
