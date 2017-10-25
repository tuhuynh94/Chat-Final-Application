package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;

public class FriendModel {

    private String m_friend_username;
    private Date m_birthday;
    private Date m_add_at;
    private String m_friend_phone;
    private String m_email;

    //getter
    public String getFriend_phone() {
        return m_friend_phone;
    }
    public void setFriend_phone(String friend_phone) {
        this.m_friend_phone = friend_phone;
    }

    public String get_username() {
        return m_friend_username;
    }

    public String get_email() {
        return m_email;
    }

    public void set_email(String _email) {
        this.m_email = _email;
    }

    //Setter
    public void setFriend_username(String friend_username) {
        this.m_friend_username = friend_username;
    }

    public Date get_birthday() {
        return m_birthday;
    }

    public void setBirthday(Date birthday) {
        this.m_birthday = birthday;
    }

    public Date get_add_at() {
        return m_add_at;
    }

    public void setAdd_at(Date add_at) {
        this.m_add_at = add_at;
    }
}
