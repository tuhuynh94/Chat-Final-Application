package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.server.PHPServer;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observable;

public class Login extends Observable {

    private static Emitter.Listener verifyCode = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean resultVerifyCode;
            try {
                resultVerifyCode = data.getBoolean("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private static Emitter.Listener getVerifyCode = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            boolean result;
            try {
                result = data.getBoolean("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public static void onCreate() {
        Server.getSocket().on("return_verfication_code", getVerifyCode);
        Server.getSocket().on("return_verfication", verifyCode);
    }

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

    public static void sendVerifyCode(String code) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("code", code);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("respose", json);
    }

    public static void getVerifyCode(String phone) {
        Server.getSocket().connect();
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("phone", phone);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request", json);
    }

    public static void sendPass(String pass) {
        Server.getSocket().connect();
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("pass", pass);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("register", json);
    }

}
