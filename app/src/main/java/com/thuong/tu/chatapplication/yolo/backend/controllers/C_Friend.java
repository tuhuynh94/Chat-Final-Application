package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Friends;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Converter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class C_Friend {
    private static Uri.Builder builder = null;
    public static void onCreate() {
        Server.getSocket().on("invite_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String sys_msg = "";
                try {
                    String from = data.getString("from");
                    String from_user = data.getString("from_username");
                    sys_msg = from_user + " want to make friend with you";
                    InvitationModel invitation = new InvitationModel();
                    invitation.setFromPhone(from);
                    invitation.setFromUser(from_user);
                    Server.owner.set_Invite_friends(invitation);
                    EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.ADD_FRIEND));

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.ADD_FRIEND));
                }
            }
        });
        Server.getSocket().on("return_response_invite_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    boolean is_accept = data.getBoolean("is_accept");
                    String from = data.getString("from");
                    String from_user = data.getString("from_username");
                    Date birthday = Converter.stringToDate(data.getString("birthday"));

                    String sys_msg = "";
                    if (is_accept) {
                        FriendModel friend = Friends.addFriend(from);
                        sys_msg = from_user + " accepted your invitation.";
                        n_add_friend(friend);
                        EventBus.getDefault()
                                .post(new OnResultFriend(sys_msg, OnResultFriend.Type.ACCEPT_ADD_FRIEND));

                    } else {
                        sys_msg = from_user + " denied your invitation.";
                        EventBus.getDefault()
                                .post(new OnResultFriend(sys_msg, OnResultFriend.Type.DENY_ADD_FRIEND));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    //TODO ERROR
                }
            }
        });
        Server.getSocket().on("un_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String friend_phone = data.getString("friend_phone");
                    String friend_name = data.getString("friend_name");
                    un_friend(friend_phone, "false");
                    EventBus.getDefault().post(new OnResultFriend("", OnResultFriend.Type.UN_FRIEND));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new OnResultFriend("", OnResultFriend.Type.UN_FRIEND));
                }
            }
        });
        Server.getSocket().on("broadcast_all_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String sys_msg = "";
                try {
                    String type = data.getString("type");
                    String user = data.getString("user");
                    if (type.equals("online")) {
                        sys_msg = user + " is online";
                        EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.BROADCAST_FRIENDS_ONLINE));
                    }
                    if (type.equals("offline")) {
                        sys_msg = user + " is online";
                        EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.BROADCAST_FRIENDS_OFFNLINE));
                    }
                    if (type.equals("update_info")) {
                        // update infor of that friend
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //sau khi da gui len server del invitation
        Server.getSocket().on("answered_invitation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String from = data.getString("from");
                    InvitationModel invitation = Server.owner.getSingleInvitaion(from);
                    Server.owner.get_Invite_friends().remove(invitation);
                    //TODO update friend + invitation UI
                    EventBus.getDefault().post(new OnResultFriend("", OnResultFriend.Type.ANSWERED_INVITATION));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    //notify to node inform accept add friend -- add new friend to socket friends list
    private static void n_add_friend(FriendModel _friend) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", _friend.getFriend_phone());
        data.put("email", _friend.get_email());
        data.put("birthday", _friend.get_birthday().toString());
        data.put("username", _friend.get_username());
        data.put("add_at", _friend.get_add_at().toString());

        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("update_add_friend", json);
    }
    public static void OnDestroy() {
        Server.getSocket().off("invite_friend");
        Server.getSocket().off("return_response_invite_friend");
        Server.getSocket().off("un_friend");
        Server.getSocket().off("broadcast_all_friend");
    }
    /**
     * send mes to server to load all friend
     */
    public static void sendUpdateFriend() {
        Server.getSocket().emit("request_load_friend");
    }
    /**
     * send request to add friend
     *
     * @param other_phone
     */
    public static void add_friend(String other_phone) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", other_phone);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_add_friend", json);
    }
    /**
     * response invitation add friend
     *
     * @param is_accept
     * @param other_phone
     */
    public static void response_add_friend(boolean is_accept, String other_phone) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("other_phone", other_phone);
        data.put("is_accept", is_accept ? "true" : "false");
        if (is_accept) {
            FriendModel friend = Friends.addFriend(other_phone);
            data.put("email", friend.get_email());
            data.put("birthday", friend.get_birthday().toString());
            data.put("username", friend.get_username());
            data.put("add_at", friend.get_add_at().toString());
        }

        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response_add_friend", json);
    }
    /**
     * unfriend
     * @param other_phone
     * @param flat default true - true:send request un friend | false: receive notify un friend
     */
    public static void un_friend(String other_phone, String flat) {
        HashMap<String, String> data = new HashMap<>();
        data.put("other_phone", other_phone);
        data.put("flat", flat);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("un_friend", json);
        Server.owner.get_listFriends().removeIf(k -> k.getFriend_phone().equals(other_phone));
        Friends.unFriend(other_phone);
    }

    public static class OnResultFriend {
        String msg;
        Type type;

        public OnResultFriend(String msg, Type type) {
            this.type = type;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public Type getType() {
            return type;
        }
        public void setType(Type type) {
            this.type = type;
        }

        public enum Type {
            ADD_FRIEND,
            ACCEPT_ADD_FRIEND,
            DENY_ADD_FRIEND,
            UN_FRIEND,
            BROADCAST_FRIENDS_ONLINE,
            BROADCAST_FRIENDS_OFFNLINE,
            ANSWERED_INVITATION
        }
    }
}
