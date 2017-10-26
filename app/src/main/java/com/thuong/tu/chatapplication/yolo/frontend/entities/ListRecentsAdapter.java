package com.thuong.tu.chatapplication.yolo.frontend.entities;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;


public class ListRecentsAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<ConversationModel> conversations;

    public ListRecentsAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<ConversationModel> conversations) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.conversations = conversations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ConversationModel conversation = conversations.get(position);
        Holder holder = Holder.init(convertView);
        holder.name.setText(conversation.getConversation_name());
        holder.content.setText(conversation.get_last_message().get_message());
        return convertView;
    }

    public static class Holder{
        static CircleImageView avatar;
        static TextView name,content;
        public Holder(CircleImageView avatar, TextView name, TextView content){
            this.avatar = avatar;
            this.name = name;
            this.content = content;
        }
        private static Holder init(View convertView){
            CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            Holder holder = new Holder(avatar, name, content);
            return holder;
        }
    }
}
