package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ClientModel {

    private String m_phone;
    private String m_username;
    private Date m_birthday;
    private String m_email;
    private String m_allConversation = "";//split by ;
    private String m_imageSource = "";
    private String m_status;

    private List<FriendModel> m_list_friends = new ArrayList<>();
    private List<ConversationModel> m_list_conversations = new ArrayList<>();
    private List<InvitationModel> m_invite_friends = new ArrayList<>();

    private HashMap<String, ArrayList<MessageModel>> m_hash_messages = new HashMap<>();

    public String getM_imageSource() {
        return m_imageSource;
    }

    public void setImageSource(String m_imageSource) {
        this.m_imageSource = m_imageSource;
    }

    public String getM_status() {
        return m_status;
    }

    public void setStatus(String m_status) {
        this.m_status = m_status;
    }

    public String getAllConversation() {
        return m_allConversation;
    }

    public void setAllConversation(String m_allConversation) {
        this.m_allConversation = m_allConversation;
    }

    public void addAllConversation(String m_allConversation) {
        this.m_allConversation += this.m_allConversation != null ? "," : "" + m_allConversation;
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

    public ArrayList<MessageModel> getAllMessageInConversation(String conversation_id) {
        return this.m_hash_messages.get(conversation_id);
    }

    public List<FriendModel> getListFriends() {
        return this.m_list_friends;
    }

    public void addFriend(FriendModel friendModel) {
        this.m_list_friends.add(friendModel);
    }

    public List<ConversationModel> getListConversation() {
        return this.m_list_conversations;
    }

    public List<InvitationModel> getInvite_friends() {
        return this.m_invite_friends;
    }

    public void setInvite_friends(InvitationModel invite_friends) {
        this.m_invite_friends.add(invite_friends);
    }

    public void setMessage(String id, MessageModel messageModel) {
        if (this.m_hash_messages.containsKey(id)) {
            ArrayList<MessageModel> ms = new ArrayList<>();
            ms = this.m_hash_messages.get(id);
            ms.add(messageModel);
            this.m_hash_messages.put(id, ms);
        } else {
            ArrayList<MessageModel> ms = new ArrayList<>();
            ms.add(messageModel);
            this.m_hash_messages.put(id, ms);
        }
    }

    public void add_conversations(ConversationModel m_conversations) {
        this.m_list_conversations.add(m_conversations);
    }

    public void removeFriend(String other_phone) {
        this.m_list_friends.removeIf(k -> k.getFriend_phone().equals(other_phone));
    }

    //kick user in a conversation by id
    public void kickMemberInConversation(String conversation_id, String phone) {
        ConversationModel a = this.getConversationByID(conversation_id);
        String[] arr = a.getMember().split(",");
        for (String item : arr) {
            if (item.trim().equals(phone)) {
                item = "";
            }
        }
        a.setMember(arr.toString());
    }

    // get obj conversation in list conversations
    public ConversationModel getConversationByID(String conversation_id) {
        return (ConversationModel) this.m_list_conversations.stream().filter(i -> i.getConversation_id() == conversation_id);
    }

    //add new mem in list conversation
    public void addMemberInConversation(String conversation_id, String phone) {
        ConversationModel a = this.getConversationByID(conversation_id);
        a.addNewMem(phone);
    }
}

