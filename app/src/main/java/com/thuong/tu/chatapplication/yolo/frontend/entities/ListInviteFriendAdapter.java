package com.thuong.tu.chatapplication.yolo.frontend.entities;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.controllers.C_Friend;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.InvitationModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListInviteFriendAdapter extends ArrayAdapter<InvitationModel> {
    Activity context;
    int resource;
    ArrayList<InvitationModel> invitations;
    Holder holder;

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
        holder = initHolder(convertView);
        holder.name.setText(invitation.getFromUser());
        if(invitation.get_image_source() != null && !invitation.get_image_source().isEmpty()){
            Picasso.with(getContext()).load(invitation.get_image_source()).into(holder.avatar);
        }
        setOnClickItem(position);
        return convertView;
    }

    private void setOnClickItem(int pos) {
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Friend.response_add_friend(true, invitations.get(pos).getFromPhone());
            }
        });
        holder.decline.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_Friend.response_add_friend(false, invitations.get(pos).getFromPhone());
            }
        }));
    }

    @Override
    public int getCount() {
        return invitations.size();
    }

    @Nullable
    @Override
    public InvitationModel getItem(int position) {
        return invitations.get(position);
    }

    @Override
    public int getPosition(@Nullable InvitationModel item) {
        return invitations.indexOf(item);
    }

    private Holder initHolder(View convertView) {
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        Button accept = (Button) convertView.findViewById(R.id.accept);
        Button decline = (Button) convertView.findViewById(R.id.decline);
        Holder holder = new Holder(avatar, name, accept, decline);
        return holder;
    }

    public class Holder {
        CircleImageView avatar;
        TextView name;
        Button accept, decline;

        public Holder(CircleImageView avatar, TextView name, Button accept, Button decline) {
            this.avatar = avatar;
            this.name = name;
            this.accept = accept;
            this.decline = decline;
        }
    }
}
