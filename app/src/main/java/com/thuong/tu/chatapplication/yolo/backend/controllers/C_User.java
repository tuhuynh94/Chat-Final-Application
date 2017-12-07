package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.User;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class C_User {

    public static void onCreate(){
        Server.getSocket().on("change_avatar", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    byte[] bytes = (byte[]) data.get("image");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    OnResultUser onResultUser = new OnResultUser("", OnResultUser.Type.CHANGE_AVATAR);
                    onResultUser.setBitmap(bitmap);
                    EventBus.getDefault().post(onResultUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void OnChangeUserInfo(boolean gender, String password, String username, Date birthday, String email) {
        Server.owner.set_Email(email);
        Server.owner.set_Birthday(birthday);
        Server.owner.set_gender(gender);
        Server.owner.set_Username(username);
        User.OnChangeUserInfo(password);

        HashMap<String, String> p = new HashMap<>();
        p.put("username", username);
        p.put("gender", gender ? "1" : "0");
        p.put("password", password);
        p.put("birthday", birthday.toString());
        p.put("email", email);
        p.put("phone", Server.owner.get_Phone());

        Server.getSocket().emit("update_user", new JSONObject(p));

    }

    public static class OnResultUser {
        String msg;
        Bitmap bitmap;
        Type type;

        public OnResultUser(String msg, Type type) {
            this.type = type;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Type getType() {
            return type;
        }
        public void setType(Type type) {
            this.type = type;
        }

        public enum Type {
            CHANGE_AVATAR
        }
    }
}
