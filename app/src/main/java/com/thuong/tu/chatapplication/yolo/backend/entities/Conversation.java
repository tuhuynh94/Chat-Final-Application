package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;

/**
 * Created by Thuong on 10/10/2017.
 */

public class Conversation {
    public int conversation_id;
    public String conversation_name;
    public String member; //cách nhau bởi;
    public String creator;
    public Date created_at;
    public Date updated_at;
}
