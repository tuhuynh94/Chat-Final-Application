package com.thuong.tu.chatapplication.yolo.backend.server;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.utils.Constant;

import java.net.URISyntaxException;

public class Server {
    private static final Server ourInstance = new Server();
    public static ClientModel owner = new ClientModel();
    private static Socket mSocket;

        {
            try {
                mSocket = IO.socket(Constant.ip_address + ":" + Constant.server_port);
            } catch (URISyntaxException e) {}
        }

    public static Server getInstance() {
        return ourInstance;
    }

    public static Socket getSocket(){
        return mSocket;
    }
}
