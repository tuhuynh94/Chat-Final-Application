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

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListRecentsAdapter extends ArrayAdapter<ConversationModel>{
    Activity context;
    int resource;
    ArrayList<ConversationModel> conversationModels;

    public ListRecentsAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<ConversationModel> conversationModels) {
        super(context, resource, conversationModels);
        this.context = context;
        this.resource = resource;
        this.conversationModels = conversationModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);
        ConversationModel conversation = conversationModels.get(position);
        Holder holder = initHolder(convertView);
        holder.name.setText(conversation.getConversation_name());
        if(conversation.get_last_message() != null){
            holder.content.setText(conversation.get_last_message().get_message());
        }
        return convertView;
    }

    private Holder initHolder(View convertView) {
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        Holder holder = new Holder(avatar, name, content);
        return holder;
    }

    public class Holder{
        CircleImageView avatar;
        TextView name, content;
        public Holder(CircleImageView avatar, TextView name, TextView status){
            this.avatar = avatar;
            this.name = name;
            this.content = status;
        }
    }
}
