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
                    //TODO event have invitation
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
                        Server.owner.get_listFriends().add(friend);
                        FriendModel _friend = Friends.addFriend(from);
                        n_add_friend(friend);
                        //TODO event accept friend
                    } else {
                        //TODO  event denny friend
                    }

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
                    //TODO event un friend
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //notify to node inform accept add friend -- add new friend to socket friends list
    private static void n_add_friend(FriendModel _friend) {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put("other_phone", _friend.getFriend_phone());
        FriendModel friend = Friends.addFriend(_friend.getFriend_phone());
        data.put("email", _friend.get_email());
        data.put("birthday", _friend.get_birthday().toString());
        data.put("username", _friend.get_username());
        data.put("add_at", _friend.get_add_at().toString());

        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("update_add_friend", json);
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
     * @param _friend
     */
    public static void response_add_friend(boolean is_accept, FriendModel _friend) {
        FriendModel friend = null;
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", _friend.getFriend_phone());
        data.put("is_accept", is_accept ? "True":"Fasle");

        if (is_accept) {
            Server.owner.get_listFriends().add(_friend);
            friend = Friends.addFriend(friend.getFriend_phone());
            data.put("email", friend.get_email());
            data.put("birthday", friend.get_birthday().toString());
            data.put("username", friend.get_username());
            data.put("add_at", friend.get_add_at().toString());
        }

        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response_add_friend", json);

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
        Server.owner.get_listFriends().removeIf(k -> k.getFriend_phone().equals(other_phone));
        Friends.unFriend(other_phone);
    }
}
