package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.thuong.tu.chatapplication.yolo.backend.API.Messages;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

public class C_Message {
    public static void onCreate() {

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
        //TODO send to sever -- update node server
    }

    public static void editMessage(String message_id, String conversation_id, String content) {
        MessageModel messages = Server.owner.get_SingleMessage(conversation_id, message_id);
        messages.set_message(content);
        Messages.editMessage(conversation_id, message_id, content);
        //TODO send to sever -- update node server
    }
}

