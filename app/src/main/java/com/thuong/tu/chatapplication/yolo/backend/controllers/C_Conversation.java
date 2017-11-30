package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.API.Messages;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Converter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class C_Conversation {
    private static Uri.Builder builder = null;
    private static JSONObject data;

    public static void onCreate() {
        Server.getSocket().on("r_add_conversation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                data = (JSONObject) args[0];
                String sys_msg = "";
                try {
                    String conversation_id = data.getString("conversation_id");
                    String consersation_name = data.getString("conversation_name");
                    String owner = data.getString("owner");
                    sys_msg = owner + " created new conversation with you and other";

                    if (!Server.owner.get_username().equals(owner)) {
                        //get new conversations by PHP
                        Conversations.getSingleConversation(conversation_id);
                    }
                    //duoc ng khác add vào conversation
                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, OnResultConversation.Type.add_conversation));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, OnResultConversation.Type.add_conversation));
                }
            }
        });
        Server.getSocket().on("r_add_new_mem", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                data = (JSONObject) args[0];
                String sys_msg = "";
                try {
//                    String type = data.getString("type");
//                    String content = data.getString("content");
                    String user_add = data.getString("user_add");
                    String user_join = data.getString("user_join");
                    String conversation_id = data.getString("conversation_id");
                    //send to PHP add a conversation
                    ConversationModel conversation = Conversations.getSingleConversation(conversation_id);
                    Server.owner.add_AllConversation(conversation_id);
                    //load msg in that conversation
                    Messages.getMsgInConversation(conversation_id);

                    if (user_add.equals(Server.owner.get_username())) {
                        sys_msg = "You added " + user_join + " to your conversation.";
                    } else if (user_join.equals(Server.owner.get_username())) {
                        sys_msg = user_add + " added you to this conversation.";
                    } else {
                        sys_msg = user_add + " added " + user_join + " to your conversation.";
                    }

                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, C_Conversation.OnResultConversation.Type.add_member));

                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, C_Conversation.OnResultConversation.Type.add_member));
                }
            }
        });
        Server.getSocket().on("r_leave_conversation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                data = (JSONObject) args[0];
                String sys_msg = "";

                try {
                    boolean check = data.getBoolean("check");
                    if (check) {
                        String username = data.getString("username") + "";
                        sys_msg = "You had left";
                    } else {
                        String kicked = data.getString("user_kicked");
                        sys_msg = kicked + " had left";
                    }
                    EventBus.getDefault()
                            .post(new OnResultConversation(sys_msg, OnResultConversation.Type.kick_member));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault()
                            .post(new OnResultConversation(sys_msg, OnResultConversation.Type.kick_member));
                }

            }
        });
        Server.getSocket().on("broadcast_all_conversation", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                JSONObject tmp = null;
                String sys_msg = "";
                try {
                    tmp = data.getJSONObject("content");
                    String phone = tmp.getString("phone");
                    String username = tmp.getString("username");
                    String conver_id = tmp.getString("conversation_id");
                    // update thong tin ket ban

                    ConversationModel con = Server.owner.get_ConversationByID(conver_id);
                    ClientModel client = con.getInforOfMember().get(phone);
                    client.set_Username(username);
                    client.set_ImageSource(tmp.getString("image_source"));
                    client.set_gender(tmp.getString("gender").equals("1"));
                    client.set_Birthday(Converter
                            .stringToDate(tmp.getString("birthday")));
                    client.set_Email(tmp.getString("email"));
                    con.getInforOfMember().remove(phone);
                    con.getInforOfMember().put(phone, client);

                    EventBus.getDefault().post(new C_Friend.OnResultFriend(sys_msg, C_Friend.OnResultFriend.Type.UPDATE_USER_INFO));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void onDestroy() {
        Server.getSocket().off("r_add_new_mem");
        Server.getSocket().off("r_add_conversation");
    }
    public static void sendUpdateConversation() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("conversation_id", Server.owner.get_AllConversation());
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_load_conversation", json);
    }
    /**
     * @param name conversation's name
     * @param mem  id all phone number of member | split by ',' | contain owner
     */
    public static void createConversation(String name, String mem) {
        //send to PHP
        ConversationModel conversation = Conversations.createConversation(name, mem);
        Server.owner.add_AllConversation(conversation.getConversation_id());

        //send to sever -- update node server
        HashMap<String, String> p = new HashMap<>();
        p.put("conversation_id", conversation.getConversation_id());
        p.put("conversation_name", conversation.getConversation_name());
        p.put("members", conversation.getMember());
        Server.getSocket().emit("add_new_conversation", new JSONObject(p));
    }
    //check
    public static void addNewMember(String conversation_id, String phone, String username) {
        ConversationModel conversation = Server.owner.get_ConversationByID(conversation_id);
        // add to current conversation
        conversation.addNewMem(phone);
        //send to PHP
        Conversations.addNewMember(conversation_id, phone);
        //send to sever -- update node server
        HashMap<String, String> data = new HashMap<>();
        data.put("conversation_id", conversation_id);
        data.put("other_phone", phone);
        data.put("username", username);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("add_new_mem", json);
    }
    //check
    public static void kickMember(String conversation_id, String phone) {
        removeMember(conversation_id, phone);
        Conversations.removeMember(conversation_id);

        //send to sever -- update node server
        HashMap<String, String> p = new HashMap<>();
        p.put("conversation_id", conversation_id);
        p.put("phone", phone);
        p.put("username", phone);
        Server.getSocket().emit("kick_member", new JSONObject(p));
    }
    //check
    public static void leave(String conversation_id) {
        removeMember(conversation_id, Server.owner.get_Phone());
        Conversations.removeMember(conversation_id);

        //send to sever -- update node server
        HashMap<String, String> p = new HashMap<>();
        p.put("conversation_id", conversation_id);
        p.put("phone", Server.owner.get_Phone());
        p.put("username", Server.owner.get_Phone());
        Server.getSocket().emit("leave_conversation", new JSONObject(p));
    }

    private static void removeMember(String conversation_id, String phone) {
        ConversationModel a = Server.owner.get_ConversationByID(conversation_id);
        String[] array = a.getMember().split(",");
        StringBuilder k = new StringBuilder("");
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals(phone)) {
                k.append(array[i] + ",");
            }
        }
        a.setMember(k.toString());
    }

    public static class OnResultConversation {
        String msg;
        Type type;

        public OnResultConversation(String msg, Type type) {
            this.msg = msg;
            this.type = type;
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
            add_conversation, //add into new conversation
            add_member,
            kick_member,
            leave_conversation
        }
    }
}
