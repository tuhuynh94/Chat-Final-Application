package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Invite_Friend {
    private static JSONArray jsonArray = null;

    public static void loadInviteFriend(Uri.Builder builder) {
        String url = uService.m_host + uService.m_invite_friend;
        loadInviteFriend(uService.execute(builder, url));
    }

    private static void loadInviteFriend(String execute) {
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            InvitationModel invitation = new InvitationModel();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                invitation.setFromPhone(jsonObject.getString("from_phone"));
                invitation.setFromUser(jsonObject.getString("from_user"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Server.owner.setInvite_friends(invitation);
        }
    }
}
