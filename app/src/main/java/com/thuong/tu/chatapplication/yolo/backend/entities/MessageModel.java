package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.io.Serializable;
import java.util.Date;

public class MessageModel implements Serializable{

    private String m_creator;
    private String m_message;
    private int m_is_send;
    private Date m_create_at;
    private String m_message_id;
    private String m_conversation_id;
    private boolean m_is_creator;
    //Getter
    public String get_conversation_id() {
        return m_conversation_id;
    }
    public void set_conversation_id(String _conversation_id) {
        this.m_conversation_id = _conversation_id;
    }
    public String get_message_id() {
        return m_message_id;
    }
    public void set_message_id(String _message_id) {
        this.m_message_id = _message_id;
    }
    public String get_creator() {
        return m_creator;
    }
    //Setter
    public void set_creator(String _creator) {
        this.m_creator = _creator;
    }
    public boolean get_is_creator() {
        return m_is_creator;
    }
    public void set_is_creator(boolean _is_creator) {
        this.m_is_creator = _is_creator;
    }
    public String get_message() {
        return m_message;
    }
    public void set_message(String m_message) {
        this.m_message = m_message;
    }
    public int is_send() {
        return m_is_send;
    }
    public Date get_create_at() {
        return m_create_at;
    }
    public void set_create_at(Date _create_at) {
        this.m_create_at = _create_at;
    }
    public void set_is_send(int _is_send) {
        this.m_is_send = _is_send;
    }


}
