package com.thuong.tu.chatapplication.yolo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by gardo on 12/12/2017.
 */

public class GalleryManager {

    public static void takePicture(Activity activity, int REQUEST_TAKE_PHOTO){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
    public static void choosePicture(Activity activity, int REQUEST_CHOOSE_PHOTO){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
}
