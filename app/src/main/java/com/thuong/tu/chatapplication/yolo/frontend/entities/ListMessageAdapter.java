package com.thuong.tu.chatapplication.yolo.frontend.entities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.library.bubbleview.BubbleTextView;
import com.squareup.picasso.Picasso;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.MessageModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListMessageAdapter extends ArrayAdapter<MessageModel> {
    Activity context = null;
    ArrayList<MessageModel> messages;
    int layoutId;

    public ListMessageAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<MessageModel> messages) {
        super(context, resource, messages);
        this.context = context;
        this.messages = messages;
        this.layoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (messages.size() != 0) {
            MessageModel message = messages.get(position);
            String image_url = null;
            if (message.get_is_creator()) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.messages_send_template, null);
                image_url = Server.owner.get_imageSource();
            } else {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(R.layout.messages_receive_template, null);
                ClientModel client = Server.owner.get_ConversationByID(message.get_conversation_id()).getInforOfMember().get(message.get_creator());
                if(client != null){
                    image_url = Server.owner.get_ConversationByID(message.get_conversation_id()).getInforOfMember().get(message.get_creator()).get_imageSource();
                }
            }
            Holder holder = init(convertView);
            holder.text.setText(message.get_message());
            if(image_url != null && !image_url.isEmpty()){
                Picasso.with(getContext()).load(image_url).into(holder.avatar);
            }
        }
        return convertView;
    }

    public static class Holder {
        de.hdodenhof.circleimageview.CircleImageView avatar;
        com.github.library.bubbleview.BubbleTextView text;

        public Holder(de.hdodenhof.circleimageview.CircleImageView avatar, com.github.library.bubbleview.BubbleTextView text) {
            this.text = text;
            this.avatar = avatar;
        }
    }

    public Holder init(View convertView) {
        de.hdodenhof.circleimageview.CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        com.github.library.bubbleview.BubbleTextView text = (BubbleTextView) convertView.findViewById(R.id.message_text);
        Holder holder = new Holder(avatar, text);
        return holder;
    }
}


