package com.thuong.tu.chatapplication.yolo.backend.controllers;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.greenrobot.eventbus.EventBus;
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
                        EventBus.getDefault().post(new OnResultRegister(result, OnResultRegister.Type.phone));
                    } else {
                        OnResultRegister event = new OnResultRegister(result, OnResultRegister.Type.phone);
                        event.text = data.getString("info");
                        EventBus.getDefault().post(event);
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
                    EventBus.getDefault().post(new OnResultRegister(result, OnResultRegister.Type.code));
                } catch (JSONException e) {
                    e.printStackTrace();
                    result = false;
                    OnResultRegister event = new OnResultRegister(result, OnResultRegister.Type.code);
                    event.text = e.getMessage();
                    EventBus.getDefault().post(event);
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
                    EventBus.getDefault().post(new OnResultRegister(result, OnResultRegister.Type.register));
                } catch (JSONException e) {
                    e.printStackTrace();
                    result = false;
                    OnResultRegister event = new OnResultRegister(result, OnResultRegister.Type.register);
                    event.text = e.getMessage();
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
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("phone", phone);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request", json);
    }

    public static void sendPass(String pass) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("pass", pass);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("register", json);
    }

    public static class OnResultRegister {
        boolean result;
        String text;
        public enum Type{
            code,
            register,
            phone
        }
        Type type;
        public OnResultRegister(boolean result, Type type){
            this.result = result;
            this.type = type;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }
}
