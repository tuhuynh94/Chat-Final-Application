package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.User;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.Converter;

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
                    String path = data.getString("image_path");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    OnResultUser onResultUser = new OnResultUser("", OnResultUser.Type.CHANGE_AVATAR);
                    onResultUser.setBitmap(bitmap);
                    onResultUser.setPath(Constant.M_HOST + "/chat/image_user/" + path);
                    EventBus.getDefault().post(onResultUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updateUser(boolean gender, String phone, String username, Date birthday, String email, String image_source) {
        Date tmp = Converter.stringToDate(birthday.toString());
        Server.owner.set_Email(email);
        Server.owner.set_Birthday(tmp);
        Server.owner.set_gender(gender);
        Server.owner.set_Username(username);
        Server.owner.set_ImageSource(image_source);
        User.updateUserAndOther();

        HashMap<String, String> p = new HashMap<>();
        p.put("username", username);
        p.put("gender", gender ? "1" : "0");
        p.put("birthday", tmp.toString());
        p.put("email", email);
        p.put("phone_number", Server.owner.get_Phone());
        p.put("image_source", image_source);

        Server.getSocket().emit("update_user", new JSONObject(p));
    }

    public static class OnResultUser {
        String msg;
        String path;
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


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public enum Type {
            CHANGE_AVATAR
        }
    }
}
