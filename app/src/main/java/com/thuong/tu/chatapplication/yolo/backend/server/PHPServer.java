package com.thuong.tu.chatapplication.yolo.backend.server;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.API.Friends;
import com.thuong.tu.chatapplication.yolo.backend.API.Invite_Friend;
import com.thuong.tu.chatapplication.yolo.backend.API.Messages;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;

import org.json.JSONArray;

public class PHPServer {
    private static JSONArray jsonArray = null;
    private static Uri.Builder builder = null;

    public static void LoadInfo() {
        ClientModel a = Server.owner;
        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.getPhone());

        Friends.loadFriend(builder);
        Invite_Friend.loadInviteFriend(builder);
        Conversations.loadConversation(builder);
        Messages.loadMessage(builder);
        a = Server.owner;
    }
}