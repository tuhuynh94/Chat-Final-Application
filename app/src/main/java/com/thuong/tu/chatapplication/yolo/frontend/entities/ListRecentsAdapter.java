package com.thuong.tu.chatapplication.yolo.frontend.entities;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ListRecentsAdapter extends ArrayAdapter {
    Activity context;
    int resource;

    public ListRecentsAdapter(@NonNull Activity context, @LayoutRes int resource) {
        super(context, resource);
    }
}
