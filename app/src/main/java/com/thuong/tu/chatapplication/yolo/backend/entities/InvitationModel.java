package com.thuong.tu.chatapplication.yolo.backend.entities;

public class InvitationModel {

    private String m_fromUser;
    private String m_fromPhone;
    private String m_to;

    //Getter
    public String getFromPhone() {
        return m_fromPhone;
    }

    public void setFromPhone(String from) {
        this.m_fromPhone = from;
    }

    public String getTo() {
        return m_fromUser;
    }

    //Setter
    public void setTo(String to) {
        this.m_fromUser = to;
    }

    public void setFromUser(String fromUser) {
        this.m_fromUser = fromUser;
    }
}
