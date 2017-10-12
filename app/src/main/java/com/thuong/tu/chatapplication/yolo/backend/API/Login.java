package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;
import android.util.Log;

import com.thuong.tu.chatapplication.yolo.backend.server.PHPServer;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONException;
import org.json.JSONObject;

public class Login {

    public static void signIn(String phone, String password) {
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("phone", phone);
        builder.appendQueryParameter("pw", password);
        String url = uService.m_host + uService.m_sign_in_link;

        String a = uService.execute(builder, url);

        JSONObject jsonObject = null;
        String r = "error";

        try {
            if (!a.equals(r)) {
                jsonObject = new JSONObject(a);
                Server.owner.setUsername(jsonObject.getString("username"));
                Server.owner.setPhone(jsonObject.getString("phone"));
                Server.owner.setBirthday(jsonObject.getString("birthday"));
                Server.owner.setEmail(jsonObject.getString("email"));
                Server.owner.setAllConversation(jsonObject.getString("conversations"));
                PHPServer.LoadInfo();

            } else {
//                LoginError
//                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("owner", Server.owner.getUsername());
    }
}
