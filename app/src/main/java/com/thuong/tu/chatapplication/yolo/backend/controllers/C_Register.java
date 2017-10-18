package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class C_Register {
    public static void onCreate() {
        Server.getSocket().on("return_verification_code", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                boolean result;
                try {
                    result = data.getBoolean("success");
                    if (result) {

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("return_verification", new Emitter.Listener() {
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
        });
        Server.getSocket().on("return_register", new Emitter.Listener() {
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
        });
    }

    public static void OnDestroy() {
        Server.getSocket().off("return_verification_code");
        Server.getSocket().off("return_verification");
        Server.getSocket().off("return_register");
    }

    public static void sendVerifyCode(String code) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("code", code);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response", json);
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
