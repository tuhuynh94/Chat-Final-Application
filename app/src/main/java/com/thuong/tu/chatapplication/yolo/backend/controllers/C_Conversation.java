package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONObject;

import java.util.HashMap;

public class C_Conversation {
    private static Uri.Builder builder = null;

    public static void onCreate() {

    }

    public static void onDestroy() {

    }
    public static void sendUpdateConversation() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("conversation_id", Server.owner.getAllConversation());
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_load_conversation", json);
    }

    /**
     * @param name
     * @param mem  split by ','
     * @return
     */
    public static int createConversation(String name, String mem) {
        //TODO create conversation
        Conversations.createConversation(name, mem);
        return 0;
    }

    public static void addNewMember(String conversation_id, String phone) {
        Server.owner.addAllConversation(conversation_id);
        Conversations.addNewMember(conversation_id, phone);
    }

}
