package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Conversations {

    private static JSONArray jsonArray = null;
    private static Uri.Builder builder = null;

    public static ConversationModel createConversation(String name, String mem) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_name", name);
        builder.appendQueryParameter("mem", mem);
        builder.appendQueryParameter("creator", Server.owner.get_Phone());
        String url = Constant.M_HOST + Constant.M_CONVERSATION_ADD;
        String result = uService.execute(builder, url);
        return loadConversation_R(result);
    }

    private static ConversationModel loadConversation_R(String execute) {
        ConversationModel conversation = new ConversationModel();
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                conversation.setConversation_id(jsonObject.getString("conversation_id"));
                conversation.setConversation_name(jsonObject.getString("conversation_name"));
                conversation.setCreator(jsonObject.getString("creator"));
                conversation.setCreated_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                conversation.setUpdated_at(Converter.stringToDateTime(jsonObject.getString("updated_at")));
                conversation.setMember(jsonObject.getString("member"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.getListConversation().add(conversation);
        }
        return conversation;
    }

    public static void kickMember(String conversation_id) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("name", Server.owner.get_ConversationByID(conversation_id).getMember());
        builder.appendQueryParameter("mem", Server.owner.get_ConversationByID(conversation_id).getConversation_name());

        String url = Constant.M_HOST + Constant.M_CONVERSATION_EDIT;
        String result = uService.execute(builder, url);
        loadConversation(result);
    }

    public static void addNewMember(String conversation_id, String members) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("name", Server.owner.get_ConversationByID(conversation_id).getMember());
        builder.appendQueryParameter("mem", Server.owner.get_ConversationByID(conversation_id).getConversation_name());

        String url = Constant.M_HOST + Constant.M_CONVERSATION_EDIT;
        String result = uService.execute(builder, url);
        loadConversation(result);
    }

    public static void loadConversation() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversations", Server.owner.get_AllConversation());
        String a = Server.owner.get_AllConversation();
        String url = Constant.M_HOST + Constant.M_CONVERSATION;
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
                conversation.setConversation_name(jsonObject.getString("conversation_name"));
                conversation.setCreator(jsonObject.getString("creator"));
                conversation.setCreated_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                conversation.setUpdated_at(Converter.stringToDateTime(jsonObject.getString("updated_at")));
                conversation.setMember(jsonObject.getString("member"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.getListConversation().add(conversation);
        }
    }

}
