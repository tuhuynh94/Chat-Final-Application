package com.thuong.tu.chatapplication.yolo.backend.entities;

public class InvitationModel {

    private String m_fromUser;
    private String m_fromPhone;

    //Getter
    public String getFromPhone() {
        return m_fromPhone;
    }

    public void setFromPhone(String from) {
        this.m_fromPhone = from;
    }

    public String getFromUser() {
        return m_fromUser;
    }

    public void setFromUser(String fromUser) {
        this.m_fromUser = fromUser;
    }
}
