package com.thuong.tu.chatapplication.yolo.utils;

public class Constant {

    public static final String M_HOST = "http://192.168.0.106";
    public static final String M_SERVER_PORT = "3000";

    //region link PHP

    ///Load data
    public static final String M_SIGN_IN = "/chat/login.php"; //link sign in

    public static final String M_FRIEND = "/chat/friends.php"; //load friend
    public static final String M_ADD_FRIEND = "/chat/friends_add.php"; //add friend
    public static final String M_UN_FRIEND = "/chat/friends_remove.php"; //link sign in

    public static final String M_CONVERSATION = "/chat/conversations.php"; //load conversation
    //TODO PHP
    public static final String M_ADD_CONVERSATION = "/chat/conversation_add.php"; //add invitation ;
    //TODO PHP
    public static final String M_UPDATE_CONVERSATION = "/chat/conversation_update.php"; //add new member in conversation;

    public static final String M_MESSAGE = "/chat/messages.php"; //load message
    public static final String M_INVITE_FRIEND = "/chat/invite_friend.php"; //load invitation




    //endregion
}
