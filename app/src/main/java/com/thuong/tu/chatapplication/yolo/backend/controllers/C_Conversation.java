package com.thuong.tu.chatapplication.yolo.backend.controllers;

import android.net.Uri;

import com.github.nkzawa.emitter.Emitter;
import com.thuong.tu.chatapplication.yolo.backend.API.Conversations;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

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

                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, OnResultConversation.Type.add_conversation));
                } catch (JSONException e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new C_Conversation
                            .OnResultConversation(sys_msg, OnResultConversation.Type.add_conversation));
                }
            }
        });
        Server.getSocket().on("confirm_add_new_mem", new Emitter.Listener() {
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

                    ConversationModel conversation = Server.owner.get_ConversationByID(conversation_id);
                    Server.owner.add_AllConversation(conversation_id);
                    //send to PHP add a conversation
                    Conversations.getSingleConversation(conversation_id);
                    //load msg in that conversation
//                    Messages.getMsgInConversastion(conversation_id);

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
    }
    public static void onDestroy() {
        Server.getSocket().off("confirm_add_new_mem");
        Server.getSocket().off("r_add_conversation");
    }
    public static void sendUpdateConversation() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("conversation_id", Server.owner.get_AllConversation());
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("request_load_conversation", json);
    }
    /**
     * @param name
     * @param mem  id all phone number of member | split by ',' | contain owner
     * @return
     */
    public static void createConversation(String name, String mem) {
        //TODO -- DONE send to PHP
        ConversationModel conversation = Conversations.createConversation(name, mem);
        Server.owner.add_AllConversation(conversation.getConversation_id());

        //TODO -- DONE send to sever -- update node server
        HashMap<String, String> p = new HashMap<>();
        p.put("conversation_id", conversation.getConversation_id());
        p.put("conversation_name", conversation.getConversation_name());
        p.put("members", conversation.getMember());
        Server.getSocket().emit("add_new_conversation", new JSONObject(p));
    }

    public static void addNewMember(String conversation_id, String phone, String username) {
        ConversationModel conversation = Server.owner.get_ConversationByID(conversation_id);
        // add to current conversation
        conversation.addNewMem(phone);
        //send to PHP
        Conversations.addNewMember(conversation_id, phone);
        //TODO send to sever -- update node server
        HashMap<String, String> data = new HashMap<>();
        data.put("conversation_id", conversation_id);
        data.put("other_phone", phone);
        data.put("username", username);
        JSONObject json = new JSONObject(data);
        Server.getSocket().emit("add_new_mem", json);
    }

    public static void kickMember(String conversation_id, String phone) {
        ConversationModel a = Server.owner.get_ConversationByID(conversation_id);

        String[] arr = a.getMember().split(",");
        for (String item : arr) {
            if (item.trim().equals(phone)) {
                item = "";
            }
        }

        a.setMember(arr.toString());
        Conversations.kickMember(conversation_id);

        //TODO send to sever -- update node server
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
        }
    }
}
