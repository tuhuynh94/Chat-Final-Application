package com.thuong.tu.chatapplication.yolo.frontend.entities;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.squareup.picasso.Picasso;
import com.thuong.tu.chatapplication.R;
import com.thuong.tu.chatapplication.yolo.backend.entities.ClientModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.ConversationModel;
import com.thuong.tu.chatapplication.yolo.backend.entities.FriendModel;
import com.thuong.tu.chatapplication.yolo.backend.server.Server;
import com.thuong.tu.chatapplication.yolo.utils.ImageConvert;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListRecentsAdapter extends ArrayAdapter<ConversationModel> {
    Activity context;
    int resource;
    ArrayList<ConversationModel> conversationModels;
    boolean check_status_group = false;

    public ListRecentsAdapter(@NonNull Activity context, @LayoutRes int resource, ArrayList<ConversationModel> conversationModels) {
        super(context, resource, conversationModels);
        this.context = context;
        this.resource = resource;
        this.conversationModels = conversationModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ConversationModel conversation = conversationModels.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);
        Holder holder = initHolder(convertView);
        if(conversation.getInforOfMember().size() > 2){
            holder.name.setText(conversation.getConversation_name());
            for (Map.Entry<String, ClientModel> entry : conversation.getInforOfMember().entrySet()) {
                ClientModel client = entry.getValue();
                if (!client.get_Phone().equals(Server.owner.get_Phone()) && !client.get_imageSource().isEmpty()) {
                    Glide.with(getContext()).load(client.get_imageSource()).into(holder.avatar);
                }
                if(!client.get_Phone().equals(Server.owner.get_Phone())){
                    FriendModel friend = Server.owner.get_hash_list_friends().get(client.get_Phone());
                   if(friend != null){
                       if(friend.get_status()){
                           holder.avatar.setBorderColor(getContext().getResources().getColor(R.color.online));
                       }
                       else{
                           holder.avatar.setBorderColor(getContext().getResources().getColor(R.color.offline));
                       }
                   }
                }
            }
        }
        else{
            for (Map.Entry<String, ClientModel> entry : conversation.getInforOfMember().entrySet()) {
                ClientModel client = entry.getValue();
                if (!client.get_Phone().equals(Server.owner.get_Phone())) {
                    FriendModel friend = Server.owner.getSingleFriend(client.get_Phone());
                    GlideUrl url = new GlideUrl(friend.get_image_source());
                    Glide.with(getContext()).asBitmap().load(url).into(holder.avatar);
                }
                if(!client.get_Phone().equals(Server.owner.get_Phone())){
                    FriendModel friend = Server.owner.get_hash_list_friends().get(client.get_Phone());
                    if(friend != null){
                        holder.name.setText(friend.get_username());
                        if(friend.get_status()){
                            holder.avatar.setBorderColor(getContext().getResources().getColor(R.color.online));
                        }
                        else{
                            holder.avatar.setBorderColor(getContext().getResources().getColor(R.color.offline));
                        }
                    }
                    break;
                }
            }
        }
        if (conversation.get_last_message() != null) {
            holder.content.setText(conversation.get_last_message().get_message());
        }
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        return convertView;
    }

    @Override
    public int getCount() {
        return conversationModels.size();
    }

    @Nullable
    @Override
    public ConversationModel getItem(int position) {
        return conversationModels.get(position);
    }

    @Override
    public int getPosition(@Nullable ConversationModel item) {
        return conversationModels.indexOf(item);
    }

    private Holder initHolder(View convertView) {
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        Holder holder = new Holder(avatar, name, content);
        return holder;
    }

    public class Holder {
        CircleImageView avatar;
        TextView name, content;

        public Holder(CircleImageView avatar, TextView name, TextView status) {
            this.avatar = avatar;
            this.name = name;
            this.content = status;
        }
    }
}
