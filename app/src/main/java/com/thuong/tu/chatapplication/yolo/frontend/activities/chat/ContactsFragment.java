package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListContactAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private static ContactsFragment fragment;
    ArrayList<FriendModel> friends;
    ListView list;
    ListContactAdapter adapter;
    FloatingActionButton add_group;


    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment getInstance() {
        if(fragment == null){
            fragment = new ContactsFragment();
        }
        return fragment;
    }

    //region Override Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        list = (ListView) view.findViewById(R.id.list_contacts);
        friends = Server.owner.get_listFriends();
        adapter = new ListContactAdapter(getActivity(), R.layout.contact_layout, friends);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ConversationModel> conversations = Server.owner.getListConversation();
                FriendModel friend = friends.get(position);
                boolean check = false;
                for (ConversationModel conversation : conversations) {
                    if(conversation.getInforOfMember().get(friend.getFriend_phone()) != null && conversation.getInforOfMember().size() == 2){
                        check = true;
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("conversation", conversation);
                        startActivity(intent);
                        break;
                    }
                }
                if(!check){
//                    C_Conversation.createConversation(friend.get_username(), Server.owner.get_Phone() + "," + friend.getFriend_phone() + ",");
//                    List<ConversationModel> conversationModels = Server.owner.getListConversation();
//                    ConversationModel conversationModel = conversationModels.get(conversationModels.size() - 1);
                    ConversationModel conversationModel = new ConversationModel();
                    conversationModel.setConversation_name(friend.get_username());
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("conversation", conversationModel);
                    intent.putExtra("friend", friend);
                    intent.putExtra("type",  true);
                    startActivity(intent);
                }
            }
        });
        add_group = (FloatingActionButton) view.findViewById(R.id.add_group);
        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupAddActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    //endregion


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultFriends(C_Friend.OnResultFriend onResultFriend) {
        switch (onResultFriend.getType()) {
            case UN_FRIEND:
                break;
            case ADD_FRIEND:
                break;
            case ACCEPT_ADD_FRIEND:
                adapter.notifyDataSetChanged();
                list.smoothScrollToPosition(0);
                list.setSelection(0);
                break;
            case DENY_ADD_FRIEND:
                break;
            case ANSWERED_INVITATION:
                break;
            case BROADCAST_FRIENDS_ONLINE:
                adapter.notifyDataSetChanged();
                break;
            case BROADCAST_FRIENDS_OFFNLINE:
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
