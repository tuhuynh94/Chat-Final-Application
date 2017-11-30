package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void setMsg(String msg) {
            this.msg = msg;
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
