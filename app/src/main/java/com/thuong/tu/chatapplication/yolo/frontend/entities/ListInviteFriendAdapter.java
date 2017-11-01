package com.thuong.tu.chatapplication.yolo.frontend.entities;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListInviteFriendAdapter extends ArrayAdapter<InvitationModel>{
    Activity context;
    int resource;
    ArrayList<InvitationModel> invitations;

    public ListInviteFriendAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<InvitationModel> invitations) {
        super(context, resource, invitations);
        this.context = context;
        this.resource = resource;
        this.invitations = invitations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);
        InvitationModel invitation = invitations.get(position);
        Holder holder = initHolder(convertView);
        holder.name.setText(invitation.getFromUser());
        return convertView;
    }

    private Holder initHolder(View convertView) {
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        Holder holder = new Holder(avatar, name);
        return holder;
    }

    public class Holder{
        CircleImageView avatar;
        TextView name;
        Button accept, decline;
        public Holder(CircleImageView avatar, TextView name){
            this.avatar = avatar;
            this.name = name;
        }
    }
}
