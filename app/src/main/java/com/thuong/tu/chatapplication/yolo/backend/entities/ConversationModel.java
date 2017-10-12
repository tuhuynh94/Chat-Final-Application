package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;


public class ConversationModel {
    private String m_creator;
    private Date m_created_at;
    private Date m_updated_at;
    private String m_conversation_id;
    private String m_conversation_name;
    private String m_member; //cách nhau bởi;

    //Getter
    public String getConversation_id() {
        return m_conversation_id;
    }

    //Setter
    public void setConversation_id(String conversation_id) {
        this.m_conversation_id = conversation_id;
    }

    public String getConversation_name() {
        return m_conversation_name;
    }

    public void setConversation_name(String conversation_name) {
        this.m_conversation_name = conversation_name;
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
}
