package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ClientModel {

    private String m_phone;
    private String m_username;
    private Date m_birthday;
    private String m_email;
    private String m_allConversation = "";//split by ;

    private ArrayList<FriendModel> m_friends = new ArrayList<>();
    private ArrayList<ConversationModel> m_conversation = new ArrayList<>();
    private ArrayList<InvitationModel> m_invite_friends = new ArrayList<>();

    private HashMap<String, ArrayList<MessageModel>> m_messages = new HashMap<>();

    //region other

    //endregion
    //region getter
    public String getAllConversation() {
        return m_allConversation;
    }

    //endregion
    // region setter
    public void setAllConversation(String m_allConversation) {
        this.m_allConversation = m_allConversation;
    }

    public String getPhone() {
        return this.m_phone;
    }

    public void setPhone(String phone) {
        this.m_phone = phone;
    }

    public String getUsername() {
        return this.m_username;
    }

    public void setUsername(String username) {
        this.m_username = username;
    }

    public Date getBirthday() {
        return this.m_birthday;
    }

    public void setBirthday(Date birthday) {
        this.m_birthday = birthday;
    }

    public String getEmail() {
        return this.m_email;
    }

    public void setEmail(String email) {
        this.m_email = email;
    }

    public ArrayList<MessageModel> getMessage(String conversation_id) {
        return this.m_messages.get(conversation_id);
    }

    public ArrayList<FriendModel> getFriendModels() {
        return this.m_friends;
    }

    public void setFriendModels(FriendModel friendModel) {
        this.m_friends.add(friendModel);
    }

    public ArrayList<ConversationModel> getConversation() {
        return this.m_conversation;
    }

    public ArrayList<InvitationModel> getInvite_friends() {
        return this.m_invite_friends;
    }

    public void setInvite_friends(InvitationModel invite_friends) {
        this.m_invite_friends.add(invite_friends);
    }

    public void setMessage(String id, MessageModel messageModel) {
        if (this.m_messages.containsKey(id)) {
            ArrayList<MessageModel> ms = new ArrayList<>();
            ms = this.m_messages.get(id);
            ms.add(messageModel);
            this.m_messages.put(id, ms);
        } else {
            ArrayList<MessageModel> ms = new ArrayList<>();
            ms.add(messageModel);
            this.m_messages.put(id, ms);
        }
    }

    public void setM_conversation(ConversationModel m_conversation) {
        this.m_conversation.add(m_conversation);
    }
//endregion

}

