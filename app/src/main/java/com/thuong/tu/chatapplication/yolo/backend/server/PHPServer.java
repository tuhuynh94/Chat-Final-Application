package com.thuong.tu.chatapplication.yolo.backend.server;

import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.API.Friends;
import com.thuong.tu.chatapplication.yolo.backend.API.Invite_Friend;
import com.thuong.tu.chatapplication.yolo.backend.API.Messages;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;

import org.json.JSONArray;

public class PHPServer {
    private static JSONArray jsonArray = null;

    public static void LoadInfo() {
        ClientModel a = Server.owner;
        Friends.loadFriend();
        Invite_Friend.loadInviteFriend();
        Conversations.loadConversation();
        Messages.loadMessage();
        a = Server.owner;
    }
}