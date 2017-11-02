package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Messages;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class C_Message {
    public static void onCreate() {
        Server.getSocket().on("chat_message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String friend_phone = data.getString("content");
                    String friend_name = data.getString("creator");
                    String created_at = data.getString("created_at");

                    //TODO event recieve chat msg
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void onDestroy() {

    }

    public static void removeMessage(String message_id, String conversation_id) {
        Server.owner.get_AllMessageByConversationID(conversation_id)
                .removeIf(i -> i.get_conversation_id().equals(conversation_id));
        Messages.removeMessage(message_id, conversation_id);


        //TODO send to sever -- update node server
    }

    public static void addMessage(String content, String conversation_id) {
        MessageModel ms = Messages.addMessage(content, conversation_id);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("conversation_id", conversation_id);
        data.put("msg", content);
        data.put("msg_id", ms.get_message_id());
        data.put("create_at", ms.get_create_at().toString());
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("send_msg", json);
    }

    public static void editMessage(String message_id, String conversation_id, String content) {
        MessageModel messages = Server.owner.get_SingleMessage(conversation_id, message_id);
        messages.set_message(content);
        Messages.editMessage(conversation_id, message_id, content);
        //TODO send to sever -- update node server
    }
}

