package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;

public class MessageModel {

    private String from_phone;
    private String message;
    private int is_send;
    private Date create_at;
    private String message_id;
    private String conversation_id;

    public void setIs_send(int is_send) {
        this.is_send = is_send;
    }

    //Getter
    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom_phone() {
        return from_phone;
    }

    //Setter
    public void setFrom_phone(String from_phone) {
        this.from_phone = from_phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int is_send() {
        return is_send;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }


}
