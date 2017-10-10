package com.thuong.tu.chatapplication.yolo.backend.server;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;


public class Server {
    private static final Server ourInstance = new Server();
    private static final String ip_address = "192.168.0.105";
    private static final String server_port = "3000";

    private static Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://" + ip_address + ":" + server_port);
        } catch (URISyntaxException e) {}
    }

    public static Server getInstance() {
        return ourInstance;
    }

    public static Socket getSocket(){
        return mSocket;
    }

    private Server() {
    }
}
