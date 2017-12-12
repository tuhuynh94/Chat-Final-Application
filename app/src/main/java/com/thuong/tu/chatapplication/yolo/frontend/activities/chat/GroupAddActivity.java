package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListGroupAddAdapter;

import java.util.ArrayList;

public class GroupAddActivity extends AppCompatActivity {
    ArrayList<FriendModel> friends;
    ListView list;
    ListGroupAddAdapter adapter;
    Button create;
    EditText group_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);

        list = (ListView) findViewById(R.id.list_friend_add);
        create = (Button) findViewById(R.id.btn_create);
        group_name = (EditText) findViewById(R.id.group_name);
        friends = Server.owner.get_listFriends();
        adapter = new ListGroupAddAdapter(GroupAddActivity.this, R.layout.single_list_friend_add_group, friends);
        list.setAdapter(adapter);
        StringBuilder mems = new StringBuilder(Server.owner.get_Phone() + ",");
        ArrayList<String> list_mem = new ArrayList<>();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConversationModel conversationModel = new ConversationModel();
                conversationModel.setConversation_name(group_name.getText().toString());
                Intent intent = new Intent(GroupAddActivity.this, ChatActivity.class);
                for (int i = 0; i < list.getChildCount(); i++){
                    View view = list.getChildAt(i);
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                    if(checkBox.isChecked()){
                        mems.append(friends.get(i).getFriend_phone() + ",");
                    }
                }
                intent.putExtra("conversation", conversationModel);
                intent.putExtra("type", false);
                intent.putExtra("mems", mems.toString());
                startActivity(intent);
            }
        });
    }
}
