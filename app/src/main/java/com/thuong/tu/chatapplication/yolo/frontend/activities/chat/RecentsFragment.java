package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.frontend.activities.login.CodeVerificationActivity;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListRecentsAdapter;

import java.util.ArrayList;

public class RecentsFragment extends Fragment {
    private static RecentsFragment fragment;

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
        ListView list = (ListView) view.findViewById(R.id.list_recents);
        ArrayList<ConversationModel> conversations = Server.owner.getListConversation();
        ListRecentsAdapter adapter = new ListRecentsAdapter(getActivity(), R.layout.recents_layout, conversations);
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
