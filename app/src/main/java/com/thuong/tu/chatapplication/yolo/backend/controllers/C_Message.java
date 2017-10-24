package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.thuong.tu.chatapplication.yolo.backend.API.Messages;

//TODO REVIEW all function in this class
public class C_Message {
    public static void onCreate() {

    }

    public static void onDestroy() {

    }

    public static void removeMessage(String message_id) {

    }

    public static void addMessage(String content, String conversation_id) {
        Messages.addMessage(content, conversation_id);
    }

    public static void editMessage(String message_id) {

    }
}

