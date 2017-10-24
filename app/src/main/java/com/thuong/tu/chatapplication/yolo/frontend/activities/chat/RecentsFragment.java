package com.thuong.tu.chatapplication.yolo.frontend.activities.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thuong.tu.chatapplication.R;

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
        return inflater.inflate(R.layout.fragment__recents, container, false);
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
