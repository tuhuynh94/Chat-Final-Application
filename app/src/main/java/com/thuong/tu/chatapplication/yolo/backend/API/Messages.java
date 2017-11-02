package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Messages {
    private static JSONArray jsonArray = null;
    private static Uri.Builder builder = null;

    public static MessageModel addMessage(String content, String conversation_id) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("creator", Server.owner.get_Phone());
        builder.appendQueryParameter("message", content);
        String url = Constant.M_HOST + Constant.M_MESSAGE_ADD;
        String result = uService.execute(builder, url);
        MessageModel mess =  loadMessage_R(result);
        return mess;
    }
    public static MessageModel addReceiMessage(String content, String conversation_id, String creator) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("creator", creator);
        builder.appendQueryParameter("message", content);
        String url = Constant.M_HOST + Constant.M_MESSAGE_ADD;
        String result = uService.execute(builder, url);
        MessageModel mess =  loadMessage_R(result);
        return mess;
    }

    public static void editMessage(String conversation_id, String message_id, String content) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("message_id", message_id);
        builder.appendQueryParameter("content", content);
        String url = Constant.M_HOST + Constant.M_MESSAGE_EDIT;
        uService.execute(builder, url);
    }

    public static void removeMessage(String message_id, String conversation_id) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("message_id", message_id);
        String url = Constant.M_HOST + Constant.M_MESSAGE_REMOVE;
        uService.execute(builder, url);
    }
    public static void loadMessage() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversations", Server.owner.get_AllConversation() + ",");
        String url = Constant.M_HOST + Constant.M_MESSAGE;
        String result = uService.execute(builder, url);
        loadMessage(result);
    }

    private static MessageModel loadMessage_R(String execute) {
        MessageModel ms = null;
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            ms = new MessageModel();
            String id = "";
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ms.set_create_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                ms.set_message_id(jsonObject.getString("message_id"));
                ms.set_message(jsonObject.getString("message"));
                ms.set_is_send(jsonObject.getInt("is_send"));
                ms.set_creator(jsonObject.getString("creator"));
                id = jsonObject.getString("conversation_id");
                ms.set_conversation_id(id);
                ms.set_create_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                ms.set_is_creator(ms.get_creator().equals(Server.owner.get_Phone()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.add_Message(id, ms);
        }
        return ms;
    }
    private static void loadMessage(String execute) {
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            MessageModel ms = new MessageModel();
            String id = "";
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ms.set_create_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                ms.set_message_id(jsonObject.getString("message_id"));
                ms.set_message(jsonObject.getString("message"));
                ms.set_is_send(jsonObject.getInt("is_send"));
                ms.set_creator(jsonObject.getString("creator"));
                id = jsonObject.getString("conversation_id");
                ms.set_conversation_id(id);
                ms.set_is_creator(ms.get_creator().equals(Server.owner.get_Phone()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.add_Message(id, ms);
        }
    }
}
