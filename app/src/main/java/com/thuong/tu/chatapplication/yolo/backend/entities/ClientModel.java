package com.thuong.tu.chatapplication.yolo.backend.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ClientModel implements Serializable{

    private String m_phone;
    private String m_username;
    private Date m_birthday;
    private String m_email;
    private String m_allConversation = "";//split by ;
    private String m_imageSource = "";
    private String m_status;

    private ArrayList<FriendModel> m_list_friends = new ArrayList<>();
    private ArrayList<ConversationModel> m_list_conversations = new ArrayList<>();
    private ArrayList<InvitationModel> m_invite_friends = new ArrayList<>();

    private HashMap<String, ArrayList<MessageModel>> m_hash_messages = new HashMap<>();

    public String get_imageSource() {
        return m_imageSource;
    }

    public void set_ImageSource(String m_imageSource) {
        this.m_imageSource = m_imageSource;
    }

    public String get_status() {
        return m_status;
    }

    public void set_Status(String m_status) {
        this.m_status = m_status;
    }

    public String get_Phone() {
        return this.m_phone;
    }

    public void set_Phone(String phone) {
        this.m_phone = phone;
    }

    public String get_username() {
        return this.m_username;
    }

    public void set_Username(String username) {
        this.m_username = username;
    }

    public Date get_Birthday() {
        return this.m_birthday;
    }

    public void set_Birthday(Date birthday) {
        this.m_birthday = birthday;
    }

    public String get_Email() {
        return this.m_email;
    }

    public void set_Email(String email) {
        this.m_email = email;
    }

    public ArrayList<InvitationModel> get_Invite_friends() {
        return this.m_invite_friends;
    }

    public void set_Invite_friends(InvitationModel invite_friends) {
        this.m_invite_friends.add(invite_friends);
    }

    public ArrayList<FriendModel> get_listFriends() {
        return this.m_list_friends;
    }

    public String get_AllConversation() {
        return m_allConversation.trim();
    }

    public void set_AllConversation(String m_allConversation) {
        this.m_allConversation = m_allConversation.trim();
    }

    public void add_AllConversation(String m_allConversation) {
        this.m_allConversation += this.m_allConversation != null ? "," : "" + m_allConversation;
    }
    public ArrayList<ConversationModel> getListConversation() {
        return this.m_list_conversations;
    }

    public void add_Message(String conversation_id, MessageModel messageModel) {
        ArrayList<MessageModel> ms = null;
        if (this.m_hash_messages.containsKey(conversation_id)) {
            ms = this.m_hash_messages.get(conversation_id);
            ms.add(messageModel);
            this.m_hash_messages.put(conversation_id, ms);
        } else {
            ms = new ArrayList<>();
            ms.add(messageModel);
            this.m_hash_messages.put(conversation_id, ms);
        }
        set_last_message(conversation_id, messageModel);
    }

    private void set_last_message(String conversation_id, MessageModel messageModel) {
        ConversationModel conversation = this.getListConversation().stream()
                .filter(i -> i.getConversation_id()
                        .equals(conversation_id)).findFirst().get();

        conversation.set_last_message(messageModel);
    }

    public ArrayList<MessageModel> get_AllMessageByConversationID(String conversation_id) {
        return this.m_hash_messages.get(conversation_id);
    }

    public MessageModel get_SingleMessage(String conversation_id, String message_id) {
        return this.m_hash_messages.get(conversation_id).stream()
                .filter(i -> i.get_message_id().equals(message_id)).findFirst().get();
    }
    /**
     * get obj conversation in list conversations by conversation id
     */
    public ConversationModel get_ConversationByID(String conversation_id) {
        return this.m_list_conversations.stream()
                .filter(i -> i.getConversation_id() == conversation_id).findFirst().get();
    }

    public InvitationModel getSingleInvitaion(String from) {
        return this.m_invite_friends.stream()
                .filter(i -> i.getFromPhone().equals(from)).findFirst().get();
    }

    public static class OnLastMess{
        public OnLastMess(){

        }
    }
}

