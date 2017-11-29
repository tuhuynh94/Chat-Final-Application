package com.thuong.tu.chatapplication.yolo.backend.server;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Conversation;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Message;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Register;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Contract;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;

public class Server {
    private static final Server ourInstance = new Server();
    public static ClientModel owner = new ClientModel();
    private static Socket mSocket;
        {
            IO.Options options = new IO.Options();
//            options.forceNew = true;
//            options.reconnection = true;
//            options.reconnectionDelayMax = 3;
//            options.reconnectionDelay = 1;
            try {
                mSocket = IO.socket(Constant.M_HOST + ":" + Constant.M_SERVER_PORT);
            } catch (URISyntaxException e) {}
        }

    @Contract(pure = true)
    public static Server getInstance() {
        return ourInstance;
    }

    @Contract(pure = true)
    public static Socket getSocket(){
        return mSocket;
    }

    public static void onCreate() {
        getSocket().on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //server disconnect
                EventBus.getDefault()
                        .post(new OnResultServer(OnResultServer.Type.DISCONNECT));
            }
        });
        getSocket().on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                EventBus.getDefault()
                        .post(new OnResultServer(OnResultServer.Type.CONNECT_ERROR));
            }
        });
        getSocket().on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                EventBus.getDefault()
                        .post(new OnResultServer(OnResultServer.Type.RECONNECTING));
            }
        });
    }

    public static void connectNode() {
        if (!Server.getSocket().connected()) {
            Server.getSocket().connect();
        }
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("phone", Server.owner.get_Phone());
        data.put("username", Server.owner.get_username());
        data.put("email", Server.owner.get_Email());
        data.put("birthday", Server.owner.get_Birthday().toString());
        data.put("conversation_id", Server.owner.get_AllConversation());
        data.put("gender", Server.owner.get_gender() ? "1" : "0");
        data.put("image_source", Server.owner.get_imageSource());
        getSocket().emit("login", new JSONObject(data));

        loadInfo();//send message to node to init data of this user
        listEvent();//list of message need to list in node
    }

    public static void beforDisconnet() {
        getSocket().emit("before_disconnect");
    }

    private static void listEvent() {
        C_Register.onCreate();
        C_Friend.onCreate();
        C_Message.onCreate();
        C_Conversation.onCreate();
    }

    private static void loadInfo() {
        C_Friend.sendUpdateFriend();
        C_Conversation.sendUpdateConversation();
    }

    public static class OnResultServer {
        Type type;

        public OnResultServer(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public enum Type {
            DISCONNECT,
            RECONNECTING,
            CONNECT_ERROR
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        beforDisconnet();
    }
}
