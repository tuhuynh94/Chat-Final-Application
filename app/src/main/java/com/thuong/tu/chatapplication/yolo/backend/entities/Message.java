package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;

/**
 * Created by Thuong on 10/10/2017.
 */

public class Message {
    public String message_id;
    public String from_user;
    public String to_user;
    public String message;
    public boolean is_send;
    public Date create_at;
}
