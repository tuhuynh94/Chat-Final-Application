package com.thuong.tu.chatapplication.yolo.frontend.controllers;


import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.frontend.entities.ListMessageAdapter;

import java.util.ArrayList;

public class c_Chat {
    public static void add(ArrayList<MessageModel> messages, ListMessageAdapter adapter, MessageModel message){
        messages.add(message);
        adapter.notifyDataSetChanged();
    }
}
