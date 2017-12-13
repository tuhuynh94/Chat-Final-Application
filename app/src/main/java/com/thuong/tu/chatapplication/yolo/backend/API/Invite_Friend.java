package com.thuong.tu.chatapplication.yolo.backend.API;

import android.net.Uri;

import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.Constant;
import com.thuong.tu.chatapplication.yolo.utils.uService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Invite_Friend {
    private static JSONArray jsonArray = null;
    private static Uri.Builder builder = null;

    public static void loadInviteFriend() {
        builder = new Uri.Builder();
        builder.appendQueryParameter("phone", Server.owner.get_Phone());
        String url = Constant.M_HOST + Constant.M_INVITE_FRIEND;
        String result = uService.execute(builder, url);
        loadInviteFriend(result);
    }

    private static void loadInviteFriend(String execute) {
        try {
            jsonArray = new JSONArray(execute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                InvitationModel invitation = new InvitationModel();
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    invitation.setFromPhone(jsonObject.getString("from_phone"));
                    invitation.setFromUser(jsonObject.getString("from_user"));
                    invitation.set_status(jsonObject.getString("status"));
                    invitation.set_image_source(jsonObject.getString("image_source"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Server.owner.set_Invite_friends(invitation);
            }
        }
    }
}
