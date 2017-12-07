package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListContactAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private static ContactsFragment fragment;
    ArrayList<FriendModel> friends;
    ListView list;


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
        ListContactAdapter adapter = new ListContactAdapter(getActivity(), R.layout.contact_layout, friends);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ConversationModel> conversations = Server.owner.getListConversation();
                FriendModel friend = friends.get(position);
                boolean check = false;
                for (ConversationModel conversation : conversations) {
                    if(conversation.getInforOfMember().get(friend.getFriend_phone()) != null && conversation.getInforOfMember().size() == 1){
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
                    startActivity(intent);
                }
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

}
