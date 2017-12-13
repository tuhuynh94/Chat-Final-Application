package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Message;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListMessageAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    ArrayList<MessageModel> messages;
    Button back, send, info;
    EditText input_message;
    TextView name;
    ConversationModel conversationModel;
    ListMessageAdapter adapter;
    ListView list;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.7F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        list = (ListView) findViewById(R.id.list_messages);
        Intent intent = getIntent();
        conversationModel = (ConversationModel) intent.getSerializableExtra("conversation");
        messages = new ArrayList<>();
        ArrayList<MessageModel> temp = Server.owner.get_AllMessageByConversationID(conversationModel.getConversation_id());
        if (temp != null) {
            messages.addAll(temp);
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
                if (conversationModel.getConversation_id() != null && !conversationModel.getConversation_id().isEmpty()) {
                    C_Message.addMessage(input_message.getText().toString(), conversationModel.getConversation_id());
                } else {
                    Intent i = getIntent();
                    boolean check = i.getBooleanExtra("type", false);
                    if(check){
                        FriendModel friend = (FriendModel) i.getSerializableExtra("friend");
                        C_Conversation.createConversation(Server.owner.get_username(), Server.owner.get_Phone() + "," + friend.getFriend_phone() + ",");
                    }
                    else {
                        String mems = i.getStringExtra("mems");
                        C_Conversation.createConversation(conversationModel.getConversation_name(), mems);
                    }
                }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnConversation(C_Conversation.OnResultConversation onResultConversation) {
        if (onResultConversation.getType() == C_Conversation.OnResultConversation.Type.add_conversation) {
            List<ConversationModel> conversationModels = Server.owner.getListConversation();
            conversationModel = conversationModels.get(conversationModels.size() - 1);
            C_Message.addMessage(input_message.getText().toString(), conversationModel.getConversation_id());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnMess(C_Message.OnMess onMess) {
        messages.clear();
        input_message.setText("");
        ArrayList<MessageModel> temp = Server.owner.get_AllMessageByConversationID(conversationModel.getConversation_id());
        if (temp != null) {
            messages.addAll(temp);
        }
        adapter.notifyDataSetChanged();
        list.smoothScrollToPosition(list.getHeight());
        list.setSelection(messages.size() - 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnFriendResult(C_Friend.OnResultFriend onResultFriend){
        if(onResultFriend.getType() == C_Friend.OnResultFriend.Type.UPDATE_USER_INFO){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
