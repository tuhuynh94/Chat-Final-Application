package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Conversations {
    private static JSONArray jsonArray = null;

    public static void loadConversation(Uri.Builder builder) {
        builder.appendQueryParameter("conversations", Server.owner.getAllConversation());
        String url = uService.m_host + uService.m_conversation;
        String result = uService.execute(builder, url);
        loadConversation(result);
    }

    private static void loadConversation(String execute) {
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            ConversationModel conversation = new ConversationModel();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                conversation.setConversation_id(jsonObject.getString("conversation_id"));
                conversation.setConversation_name(jsonObject.getString("add_at"));
                conversation.setCreator(jsonObject.getString("creator"));
                conversation.setCreator(jsonObject.getString("creator"));
                conversation.setCreated_at(Converter.stringToDate(jsonObject.getString("creator")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.setM_conversation(conversation);
            //Server.owner.creatConversation(conversation.getConversation_id());
        }
    }
}
