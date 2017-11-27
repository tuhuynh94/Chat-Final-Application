package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListContactAdapter;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListInviteFriendAdapter;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    private static FriendsFragment fragment;
    ListView list;
    ArrayList<InvitationModel> invatations;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment getInstance() {
        if(fragment == null){
            fragment = new FriendsFragment();
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
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        list = (ListView) view.findViewById(R.id.list);
        invatations = Server.owner.get_Invite_friends();
        ListInviteFriendAdapter adapter = new ListInviteFriendAdapter(getActivity(), R.layout.invite_friend_layout, invatations);
        list.setAdapter(adapter);
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
