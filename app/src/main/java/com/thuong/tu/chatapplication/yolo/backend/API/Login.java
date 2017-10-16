package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;
import android.util.Log;

import com.thuong.tu.chatapplication.yolo.backend.server.PHPServer;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {

    public static boolean signIn(String phone, String password) {
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("phone", phone);
        builder.appendQueryParameter("pw", password);
        String url = Constant.M_HOST + Constant.M_SIGN_IN;
        String a = uService.execute(builder, url);
        JSONObject jsonObject = null;
        String r = "error";
        boolean result = false;

        try {
            if (!a.equals(r)) {
                jsonObject = new JSONObject(a);
                Server.owner.setUsername(jsonObject.getString("username"));
                Server.owner.setPhone(jsonObject.getString("phone"));
                Server.owner.setBirthday(Converter.stringToDate(jsonObject.getString("birthday")));
                Server.owner.setEmail(jsonObject.getString("email"));
                Server.owner.setAllConversation(jsonObject.getString("conversations"));
                PHPServer.LoadInfo();
                Server.getSocket().connect();
                Server.getSocket().emit("connect_to_server");
                result = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("owner", Server.owner.getUsername());
        return result;
    }



}
