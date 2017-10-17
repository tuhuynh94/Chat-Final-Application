package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONObject;

import java.util.HashMap;

public class C_Conversation {
    public static void sendUpdateConversation() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("conversation_id", Server.owner.getAllConversation());
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_load_conversation", json);
    }
}
