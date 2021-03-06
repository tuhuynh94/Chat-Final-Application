package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

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
        //TODO add status online or offline
        Server.getSocket().on("update_status_list_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String[] phones = data.getString("phones").split(",");
                    for (String phone : phones) {
                        FriendModel fr = Server.owner.get_SingleFriend(phone);
                        if (fr != null) {
                            fr.set_status(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("invite_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String sys_msg = "";
                try {
                    String from = data.getString("from");
                    String from_user = data.getString("from_username");
                    String image_source = data.getString("image_source");
                    sys_msg = from_user + " want to make friend with you";
                    InvitationModel invitation = new InvitationModel();
                    invitation.setFromPhone(from);
                    invitation.setFromUser(from_user);
                    invitation.set_image_source(image_source);

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
                        String username = friend.get_username();
                        sys_msg = username + " accepted your invitation.";
                        //TODO UPDATE friend UI
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
            @RequiresApi(api = Build.VERSION_CODES.N)
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
        //TODO broadcast to all friend
        Server.getSocket().on("broadcast_all_friend", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String sys_msg = "";
                try {
                    String type = data.getString("type");
                    String _phone = data.getString("phone");

                    if (type.equals("online")) {
                        FriendModel friend = Server.owner.get_SingleFriend(_phone);
                        sys_msg = friend.get_username() + " is online";
                        friend.set_status(true);
                        HashMap<String, String> p = new HashMap<>();
                        p.put("phone", _phone);
                        Server.getSocket().emit("update_user_online", new JSONObject(p));
                        EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.BROADCAST_FRIENDS_ONLINE));
                    }

                    if (type.equals("offline")) {
                        FriendModel friend = Server.owner.get_SingleFriend(_phone);
                        sys_msg = friend.get_username() + " is online";
                        friend.set_status(false);
                        EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.BROADCAST_FRIENDS_OFFNLINE));
                    }

                    if (type.equals("update_info_in_friend")) {
                        JSONObject tmp = data.getJSONObject("content");
                        String phone = tmp.getString("phone_number");
                        String username = tmp.getString("username");

                        FriendModel friendModel = Server.owner.getSingleFriend(phone);
                        friendModel.setFriend_username(username);
                        friendModel.set_image_source(tmp.getString("image_source"));
                        friendModel.set_gender(tmp.getString("gender").equals("1"));
                        friendModel.setBirthday(new Date());
                        friendModel.set_email(tmp.getString("email"));
                        EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.UPDATE_USER_INFO));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("broadcast_all_invitation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                JSONObject tmp = null;
                String sys_msg = "";
                try {
                    tmp = data.getJSONObject("content");
                    String phone = tmp.getString("phone_number");
                    String username = tmp.getString("username");
                    // update thong tin ket ban
                    InvitationModel invitationModel = Server.owner.getSingleInvitaion(phone);
                    invitationModel.setFromUser(username);
                    EventBus.getDefault().post(new OnResultFriend(sys_msg, OnResultFriend.Type.UPDATE_USER_INFO));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Server.getSocket().on("answered_invitation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String from = data.getString("from");
                    JSONObject fr = data.getJSONObject("friend");
                    boolean online = data.getBoolean("online");
                    InvitationModel invitation = Server.owner.getSingleInvitaion(from);
                    Server.owner.get_Invite_friends().remove(invitation);

                    FriendModel friend = new FriendModel();
                    friend.set_email(fr.getString("email"));
                    friend.setBirthday(Converter.stringToDate(fr.getString("birthday")));
                    friend.setAdd_at(Converter.stringToDate(fr.getString("add_at")));
                    friend.setFriend_phone(fr.getString("friend_phone"));
                    friend.setFriend_username(fr.getString("username"));
                    friend.set_image_source(fr.getString("image_source"));
                    friend.set_gender(fr.getInt("gender") == 1);
                    friend.set_status(online);

                    Server.owner.get_listFriends().add(friend);

                    //TODO update friend + invitation UI
                    EventBus.getDefault().post(new OnResultFriend("", OnResultFriend.Type.ANSWERED_INVITATION));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //notify to node inform accept add friend -- add new friend to socket friends list

    /**
     private static void n_add_friend(FriendModel _friend) {
     HashMap<String, String> data = new HashMap<String, String>();
     data.put("other_phone", _friend.getFriend_phone());
     data.put("email", _friend.get_email());
     data.put("birthday", _friend.get_birthday().toString());
     data.put("username", _friend.get_username());
     data.put("add_at", _friend.get_add_at().toString());

     JSONObject json = new JSONObject(data);
     Server.getSocket().emit("update_add_friend", json);
     }*/
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
        data.put("image_source", Server.owner.get_imageSource());
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
//        if (is_accept) {
//            FriendModel friend = Friends.addFriend(other_phone);
//            data.put("email", friend.get_email());
//            data.put("birthday", friend.get_birthday().toString());
//            data.put("username", friend.get_username());
//            data.put("add_at", friend.get_add_at().toString());
//        }

        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("response_add_friend", json);
    }
    /**
     * unfriend
     * @param other_phone
     * @param flat default true - true:send request un friend | false: receive notify un friend
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
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
            UPDATE_USER_INFO, ANSWERED_INVITATION
        }
    }
}
