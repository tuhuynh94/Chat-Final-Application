package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Friends {
    private static JSONArray jsonArray = null;
    private static Uri.Builder builder = null;

    public static FriendModel addFriend(String from) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.get_Phone());
        builder.appendQueryParameter("other_phone", from);
        String url = Constant.M_HOST + Constant.M_FRIEND_ADD;
        String result = uService.execute(builder, url);
        FriendModel friendModel = loadFriend_R(result);
        return friendModel;
    }

    private static FriendModel loadFriend_R(String execute) {
        FriendModel friend = new FriendModel();
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                friend.setBirthday(Converter.stringToDate(jsonObject.getString("birthday")));
                friend.setAdd_at(Converter.stringToDateTime(jsonObject.getString("add_at")));
                friend.setFriend_phone(jsonObject.getString("friend_phone"));
                friend.set_email(jsonObject.getString("email"));
                friend.set_image_source(jsonObject.getString("image_source"));
                friend.set_gender(jsonObject.getString("gender").equals("1"));
                friend.setFriend_username(jsonObject.getString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.get_listFriends().add(friend);
            Server.owner.get_hash_list_friends().put(friend.getFriend_phone(), friend);
        }
        return friend;
    }

    public static void unFriend(String other_phone) {
        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.get_Phone());
        builder.appendQueryParameter("other_phone", other_phone);
        String url = Constant.M_HOST + Constant.M_FRIEND_REMOVE;
        uService.execute(builder, url);
    }

    public static void loadFriend() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.get_Phone());
        String url = Constant.M_HOST + Constant.M_FRIEND;
        String result = uService.execute(builder, url);
        loadFriend(result);
    }
    private static void loadFriend(String execute) {
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            FriendModel friend = new FriendModel();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                friend.setBirthday(Converter.stringToDate(jsonObject.getString("birthday")));
                friend.setAdd_at(Converter.stringToDateTime(jsonObject.getString("add_at")));
                friend.setFriend_phone(jsonObject.getString("friend_phone"));
                friend.setFriend_username(jsonObject.getString("username"));
                friend.set_email(jsonObject.getString("email"));
                friend.set_image_source(jsonObject.getString("image_source"));
                friend.set_gender(jsonObject.getString("gender").equals("1"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.get_listFriends().add(friend);
            Server.owner.get_hash_list_friends().put(friend.getFriend_phone(), friend);
        }
    }
}
