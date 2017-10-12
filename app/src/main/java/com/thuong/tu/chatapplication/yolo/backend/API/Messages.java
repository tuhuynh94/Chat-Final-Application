package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Messages {
    private static JSONArray jsonArray = null;

    public static void loadMessage(Uri.Builder builder) {
        builder.appendQueryParameter("conversations", Server.owner.getAllConversation());
        String url = uService.m_host + uService.m_message;
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
                ms.setCreate_at(Converter.stringToDateTime(jsonObject.getString("created_at")));
                ms.setMessage_id(jsonObject.getString("message_id"));
                ms.setMessage(jsonObject.getString("message"));
                ms.setIs_send(jsonObject.getInt("is_send"));
                ms.setFrom_phone(jsonObject.getString("from_phone"));
                id = jsonObject.getString("conversation_id");
                ms.setConversation_id(id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.setMessage(id, ms);
        }
    }
}
