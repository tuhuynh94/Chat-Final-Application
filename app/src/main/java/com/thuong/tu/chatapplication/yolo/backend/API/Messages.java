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

    public static void loadMessage() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversations", Server.owner.getAllConversation());
        String url = Constant.M_HOST + Constant.M_MESSAGE;
        String result = uService.execute(builder, url);
        loadMessage(result);
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
                ms.set_is_creator(ms.get_creator().equals(Server.owner.getPhone()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.setMessage(id, ms);
        }
    }

    public static void addMessage(String content, String conversation_id) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("conversation_id", conversation_id);
        builder.appendQueryParameter("from_phone", Server.owner.getPhone());
        builder.appendQueryParameter("message", content);
        String url = Constant.M_HOST + Constant.M_MESSAGE_ADD;
    }
}
