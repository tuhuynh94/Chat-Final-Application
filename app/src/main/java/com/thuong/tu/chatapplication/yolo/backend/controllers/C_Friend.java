package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Friends;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
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
                    Date birthday = Converter.stringToDate(data.getString("birthday"));
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
                    Date birthday = Converter.stringToDate(data.getString("birthday"));
                    if (is_accept) {
                        FriendModel friend = new FriendModel();
                        friend.setFriend_phone(from);
                        friend.setFriend_username(from_user);
                        friend.setBirthday(birthday);
                        friend.setAdd_at(Calendar.getInstance().getTime());
                        Server.owner.addFriend(friend);
                        Friends.addFriend(from);
                    }
                    //TODO chua co su kien nhan duoc tra loi cua invitation
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("inform_un_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String friend_phone = data.getString("friend_phone");
                    String friend_name = data.getString("friend_name");
                    un_friend(friend_phone, "false");
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
        Server.getSocket().off("inform_un_friend");
    }

    /**
     * send mes to server to load all friend
     */
    public static void sendUpdateFriend() {
        Server.getSocket().emit("request_load_friend");
    }
    /**
     * send request to add friend
     *
     * @param other_phone
     */
    public static void add_friend(String other_phone) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", other_phone);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_add_friend", json);
    }
    /**
     * response invitation add friend
     *
     * @param is_accept
     * @param friend
     */
    public static void response_add_friend(boolean is_accept, FriendModel friend) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", friend.getFriend_phone());
        data.put("is_accept", is_accept ? "True":"Fasle");
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response_add_friend", json);
        if (is_accept) {
            Server.owner.addFriend(friend);
            Friends.addFriend(friend.getFriend_phone());
        }
    }
    /**
     * unfriend
     * @param other_phone
     * @param flat        defautl true
     */
    public static void un_friend(String other_phone, String flat) {
        HashMap<String, String> data = new HashMap<>();
        data.put("other_phone", other_phone);
        data.put("flat", flat);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("un_friend", json);
        Server.owner.removeFriend(other_phone);
        Friends.unFriend(other_phone);
    }
}
