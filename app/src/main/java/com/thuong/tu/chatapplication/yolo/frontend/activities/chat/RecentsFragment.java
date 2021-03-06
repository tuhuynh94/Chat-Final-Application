package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

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
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Message;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListRecentsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class RecentsFragment extends Fragment {
    private static RecentsFragment fragment;
    private static ListView list;
    private static  ListRecentsAdapter adapter;
    ArrayList<ConversationModel> conversations;

    public RecentsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static RecentsFragment getInstance() {
        if(fragment == null){
            fragment = new RecentsFragment();
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
        View view = inflater.inflate(R.layout.fragment__recents, container, false);
        list = (ListView) view.findViewById(R.id.list_recents);
        conversations = Server.owner.getListConversation();
        adapter = new ListRecentsAdapter(getActivity(), R.layout.recents_layout, conversations);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConversationModel conversationModel = (ConversationModel) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("conversation", conversationModel);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
    //endregion

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void OnMess(C_Message.OnMess onMess){
        adapter.notifyDataSetChanged();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnConversation(C_Conversation.OnResultConversation onResultConversation){
        if(onResultConversation.getType() == C_Conversation.OnResultConversation.Type.add_conversation){
            adapter.notifyDataSetChanged();
            list.smoothScrollToPosition(0);
            list.setSelection(0);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnResultFriends(C_Friend.OnResultFriend onResultFriend){
        switch (onResultFriend.getType()){
            case BROADCAST_FRIENDS_ONLINE:
                list.invalidateViews();
                break;
            case BROADCAST_FRIENDS_OFFNLINE:
                list.invalidateViews();
                break;
            case UPDATE_USER_INFO:
                list.invalidateViews();
                break;
        }
    }
}
