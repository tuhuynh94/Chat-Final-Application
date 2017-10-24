package com.thuong.tu.chatapplication.yolo.backend.server;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.utils.Constant;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

public class Server {
    private static final Server ourInstance = new Server();
    public static ClientModel owner = new ClientModel();
    private static Socket mSocket;
        {
            try {
                mSocket = IO.socket(Constant.M_HOST + ":" + Constant.M_SERVER_PORT);
            } catch (URISyntaxException e) {}
        }

    public static Server getInstance() {
        return ourInstance;
    }

    public static Socket getSocket(){
        return mSocket;
    }

    public static void connectNode() {
        if (!Server.getSocket().connected()) {
            Server.getSocket().connect();
        }
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("phone", Server.owner.getPhone());
        data.put("username", Server.owner.getUsername());
        data.put("birthday", Server.owner.getBirthday().toString());
        JSONObject json = new JSONObject(data);
        getSocket().emit("connect_to_server", json);

        loadInfo();//in node
        listEvent();// su kien can nghe to node
    }

    private static void listEvent() {
        C_Register.onCreate();
        C_Friend.onCreate();
    }

    private static void loadInfo() {
        C_Friend.sendUpdateFriend();
        C_Conversation.sendUpdateConversation();
    }
}
