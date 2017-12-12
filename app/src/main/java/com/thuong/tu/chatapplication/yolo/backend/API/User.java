package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static Uri.Builder builder = null;
    private static JSONObject jsonObject = null;
    private static JSONArray jsonArray = null;

    // update conversation in user, not broadcast
    public static void update_TbUser() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("username", Server.owner.get_username());
        builder.appendQueryParameter("email", Server.owner.get_Email());
        builder.appendQueryParameter("birthday", Server.owner.get_Birthday().toString());
        builder.appendQueryParameter("gender", Server.owner.get_gender() ? "1" : "0");
        builder.appendQueryParameter("phone", Server.owner.get_Phone());
        builder.appendQueryParameter("image_source", Server.owner.get_imageSource());
        builder.appendQueryParameter("conversations", Server.owner.get_AllConversation());

        String url = Constant.M_HOST + Constant.M_UPDATE_USER_WITHOUT_PASS;
        String a = uService.execute(builder, url);
    }

    /*with password*/
    public static void updateUserAndOther() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("username", Server.owner.get_username());
        builder.appendQueryParameter("email", Server.owner.get_Email());
//        builder.appendQueryParameter("pass", pass);
        builder.appendQueryParameter("birthday", Server.owner.get_Birthday().toString());
        builder.appendQueryParameter("gender", Server.owner.get_gender() ? "1" : "0");
        builder.appendQueryParameter("phone_number", Server.owner.get_Phone());
        builder.appendQueryParameter("image_source", Server.owner.get_imageSource());

        String url = Constant.M_HOST + Constant.M_UPDATE_USER;
        String a = uService.execute(builder, url);
        String b = a;
    }
    public static List<ClientModel> loadUserInConversation(String member) {
        builder = new Uri.Builder();
        String[] arr = member.split(",");
        String tmp = "";
        for (String each : arr) {
            if (!each.equals("") && each != null) {
                tmp += "\'" + each + "\',";
            }
        }
        tmp += "'0'";
        builder.appendQueryParameter("members", tmp);
        String url = Constant.M_HOST + Constant.M_USERS;
        String result = uService.execute(builder, url);
        List<ClientModel> users = loadUsers_R(result);
        return users;
    }

    private static List<ClientModel> loadUsers_R(String execute) {
        List<ClientModel> tmp = new ArrayList<>();
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                ClientModel user = new ClientModel();
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    user.set_Username(jsonObject.getString("username"));
                    user.set_Birthday(Converter.stringToDate(jsonObject.getString("birthday")));
                    user.set_Email(jsonObject.getString("email"));
                    user.set_Phone(jsonObject.getString("phone"));
                    user.set_ImageSource(jsonObject.getString("image_source"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tmp.add(user);
            }
        }
        return tmp;
    }


}
