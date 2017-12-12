package com.thuong.tu.chatapplication.yolo.frontend.entities;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ListGroupAddAdapter extends ArrayAdapter<FriendModel> {
    Activity context;
    int resource;
    ArrayList<FriendModel> friends;

    public ListGroupAddAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<FriendModel> friends) {
        super(context, resource, friends);
        this.context = context;
        this.resource = resource;
        this.friends = friends;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);
        FriendModel friend = friends.get(position);
        Holder holder = initHolder(convertView);
        holder.name.setText(friend.get_username());
        if (!friend.get_image_source().isEmpty()) {
            Picasso.with(getContext()).load(friend.get_image_source()).into(holder.avatar);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Nullable
    @Override
    public FriendModel getItem(int position) {
        return friends.get(position);
    }

    @Override
    public int getPosition(@Nullable FriendModel item) {
        return friends.indexOf(item);
    }

    private Holder initHolder(View convertView) {
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        Holder holder = new Holder(avatar, name);
        return holder;
    }

    public class Holder {
        CircleImageView avatar;
        TextView name;

        public Holder(de.hdodenhof.circleimageview.CircleImageView avatar, TextView name) {
            this.avatar = avatar;
            this.name = name;
        }
    }
}
