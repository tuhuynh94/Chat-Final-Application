package com.thuong.tu.chatapplication.yolo.backend.entities;

public class InvitationModel {

    private String m_fromUser;
    private String m_fromPhone;
    private String m_status;
    private String m_image_source;

    public String get_image_source() {
        return m_image_source;
    }

    public void set_image_source(String m_image_source) {
        this.m_image_source = m_image_source;
    }

    public String get_status() {
        return m_status;
    }

    public void set_status(String m_status) {
        this.m_status = m_status;
    }


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
