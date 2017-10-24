package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
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
    public static void createConversation(String name, String mem) {
        ConversationModel conversation = Conversations.createConversation(name, mem);

        //TODO send to sever -- update node server
    }
    public static void addNewMember(String conversation_id, String phone) {
        Server.owner.addMemberInConversation(conversation_id, phone);
        Conversations.addNewMember(conversation_id, phone);

        //TODO send to sever -- update node server
    }

    public static void kickMember(String conversation_id, String phone) {
        Server.owner.kickMemberInConversation(conversation_id, phone);
        Conversations.kickMember(conversation_id);

        //TODO send to sever -- update node server
    }
}
