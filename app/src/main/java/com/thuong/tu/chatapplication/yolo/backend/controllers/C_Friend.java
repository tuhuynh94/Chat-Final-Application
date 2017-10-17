package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Friends;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class C_Friend {
    private static Uri.Builder builder = null;

    public static void onCreate() {
        Server.getSocket().on("return_invite_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String from = data.getString("from");
                    String from_user = data.getString("from_username");
                    //TODO chua co su kien nhan invitation
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("return_response_invite_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    boolean is_accept = data.getBoolean("is_accept");
                    String from = data.getString("from");
                    String from_user = data.getString("from_username");

                    //TODO chua co su kien nhan duoc tra loi cua invitation
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Server.getSocket().on("unfriend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String friend_phone = data.getString("friend_phone");
                    String friend_name = data.getString("friend_name");
                    unfriend(friend_phone, "false");
                    //TODO chua co su kien unfriend
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void OnDestroy() {
        Server.getSocket().off("return_invite_friend");
        Server.getSocket().off("return_response_invite_friend");
    }

    public static void sendUpdateFriend() {
        Server.getSocket().emit("request_load_friend");
    }

    public static void add_friend(String other_phone) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", other_phone);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_add_friend", json);
    }

    public static void response_add_friend(String is_accept, String from) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", from);
        data.put("is_accept", is_accept);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response_add_friend", json);
    }

    public static void unfriend(String other_phone, String flat) { ///flat = true
        HashMap<String, String> data = new HashMap<>();
        data.put("other_phone", other_phone);
        data.put("flat", flat);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("unfriend", json);
        Server.owner.removeFriend(other_phone);

        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.getPhone());
        builder.appendQueryParameter("other_phone", other_phone);

        Friends.addFriend(builder);
    }
}
