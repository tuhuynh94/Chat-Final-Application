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
        data.put("conversation_id", Server.owner.get_AllConversation());
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
        Server.owner.add_AllConversation(conversation.getConversation_id());

        //TODO send to sever -- update node server
    }
    public static void addNewMember(String conversation_id, String phone) {
        ConversationModel conversation = Server.owner.get_ConversationByID(conversation_id);
        conversation.addNewMem(phone);
        Conversations.addNewMember(conversation_id, phone);

        //TODO send to sever -- update node server
    }

    public static void kickMember(String conversation_id, String phone) {
        ConversationModel a = Server.owner.get_ConversationByID(conversation_id);
        String[] arr = a.getMember().split(",");
        for (String item : arr) {
            if (item.trim().equals(phone)) {
                item = "";
            }
        }
        a.setMember(arr.toString());
        Conversations.kickMember(conversation_id);

        //TODO send to sever -- update node server
    }
}
