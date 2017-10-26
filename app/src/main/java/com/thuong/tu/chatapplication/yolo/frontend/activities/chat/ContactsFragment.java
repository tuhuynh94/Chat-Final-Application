package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListContactAdapter;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    private static ContactsFragment fragment;

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
        ListView list = (ListView) view.findViewById(R.id.list_contacts);
        ArrayList<FriendModel> friends = Server.owner.get_listFriends();
        ListContactAdapter adapter = new ListContactAdapter(getActivity(), R.layout.contact_layout, friends);
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
