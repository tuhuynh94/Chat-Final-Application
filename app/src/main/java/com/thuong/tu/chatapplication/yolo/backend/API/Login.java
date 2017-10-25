package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

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
                Server.owner.set_Username(jsonObject.getString("username"));
                Server.owner.set_Phone(jsonObject.getString("phone"));
                Server.owner.set_Birthday(Converter.stringToDate(jsonObject.getString("birthday")));
                Server.owner.set_Email(jsonObject.getString("email"));
                Server.owner.set_AllConversation(jsonObject.getString("conversations"));
                Server.owner.set_ImageSource(jsonObject.getString("image_source"));
                Server.owner.set_Status(jsonObject.getString("status"));
                PHPServer.LoadInfo();
                Server.connectNode();
                result = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d("owner", Server.owner.get_username());
        return result;
    }



}
