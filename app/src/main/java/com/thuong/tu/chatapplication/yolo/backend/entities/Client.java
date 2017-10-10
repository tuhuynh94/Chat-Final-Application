package com.thuong.tu.chatapplication.yolo.backend.entities;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.thuong.tu.chatapplication.yolo.utils.uService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Thuong on 10/10/2017.
 */

public class Client {
    public String phone;
    public String username;
    public static String birthday;
    public static String email;

    public static ArrayList<Friend> friends= new ArrayList<Friend>();
    public static ArrayList<Conversation> conversations= new ArrayList<Conversation>();
    public static HashMap<String, ArrayList<Message>> messages = new HashMap<>();

    public static HashMap<String, ArrayList<Message>> getMessage() {
        return messages;
    }

    public static void setMessage( String id ,Message message) {
        if (messages.containsKey(id)){
            ArrayList<Message> ms = new ArrayList<>();
            ms =messages.get(id);
            ms.add(message);
            Client.messages.put(id,ms);
        }else {

        }

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getBirthday() {
        return birthday;
    }

    public static void setBirthday(String birthday) {
        Client.birthday = birthday;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Client.email = email;
    }

    public static ArrayList<Friend> getFriends() {
        return friends;
    }

    public static void setFriends(Friend friend) {
        Client.friends.add(friend);
    }

    public static ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public static void setConversations(Conversation conversations) {
        Client.conversations.add(conversations);
    }


}
