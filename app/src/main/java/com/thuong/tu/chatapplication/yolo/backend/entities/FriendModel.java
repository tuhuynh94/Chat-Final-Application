package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.Date;

public class FriendModel {

    private String m_friend_username;
    private Date m_birthday;
    private Date m_add_at;
    private String m_friend_phone;

    //getter
    public String getFriend_phone() {
        return m_friend_phone;
    }

    public void setFriend_phone(String friend_phone) {
        this.m_friend_phone = friend_phone;
    }

    public String getFriend_username() {
        return m_friend_username;
    }

    //Setter
    public void setFriend_username(String friend_username) {
        this.m_friend_username = friend_username;
    }

    public Date getBirthday() {
        return m_birthday;
    }

    public void setBirthday(Date birthday) {
        this.m_birthday = birthday;
    }

    public Date getAdd_at() {
        return m_add_at;
    }

    public void setAdd_at(Date add_at) {
        this.m_add_at = add_at;
    }

}
