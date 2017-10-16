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

    public static void loadFriend(Uri.Builder builder) {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.setFriendModels(friend);
        }
    }
}
