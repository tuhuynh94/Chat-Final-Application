package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.io.Serializable;
import java.util.Date;


public class ConversationModel implements Serializable{
    private String m_creator;
    private Date m_created_at;
    private Date m_updated_at;
    private String m_conversation_id;
    private String m_conversation_name;
    private String m_member; //cách nhau bởi;
    private MessageModel m_last_message;

    //Getter
    public String getConversation_id() {
        return m_conversation_id;
    }

    //Setter
    public void setConversation_id(String conversation_id) {
        this.m_conversation_id = conversation_id;
    }

    public String getMember() {
        return m_member;
    }

    public void setMember(String member) {
        this.m_member = member;
    }

    public String getCreator() {
        return m_creator;
    }

    public void setCreator(String creator) {
        this.m_creator = creator;
    }

    public Date getCreated_at() {
        return m_created_at;
    }

    public void setCreated_at(Date created_at) {
        this.m_created_at = created_at;
    }

    public Date getUpdated_at() {
        return m_updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.m_updated_at = updated_at;
    }

    public String getConversation_name() {
        return m_conversation_name;
    }

    public void setConversation_name(String conversation_name) {
        this.m_conversation_name = conversation_name;
    }

    public MessageModel get_last_message() {
        return m_last_message;
    }

    public void set_last_message(MessageModel m_last_message) {
        this.m_last_message = m_last_message;
    }

    public void addNewMem(String phone) {
        String tmp = "," + phone;
        this.m_member += tmp;
    }
}
