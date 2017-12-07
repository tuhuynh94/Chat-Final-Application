package com.thuong.tu.chatapplication.yolo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by gardo on 02/12/2017.
 */

public class ImageConvert {
    private static Target mTarget;
    private static Bitmap bitmap_result;

    public static Bitmap loadImage(Context context, String url) {

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                bitmap_result = bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(context)
                .load(url)
                .into(mTarget);
        return bitmap_result;
    }
    public static Bitmap mergeThemAll(List<Bitmap> orderImagesList) {
        Bitmap result = null;
        if (orderImagesList != null && orderImagesList.size() > 0) {
            // if two images > increase the width only
            if (orderImagesList.size() == 2)
                result = Bitmap.createBitmap(orderImagesList.get(0).getWidth() * 2, orderImagesList.get(0).getHeight(), Bitmap.Config.ARGB_8888);
                // increase the width and height
            else if (orderImagesList.size() > 2)
                result = Bitmap.createBitmap(orderImagesList.get(0).getWidth() * 2, orderImagesList.get(0).getHeight() * 2, Bitmap.Config.ARGB_8888);
            else // don't increase anything
                result = Bitmap.createBitmap(orderImagesList.get(0).getWidth(), orderImagesList.get(0).getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            for (int i = 0; i < orderImagesList.size(); i++) {
                canvas.drawBitmap(orderImagesList.get(i), orderImagesList.get(i).getWidth() * (i % 2), orderImagesList.get(i).getHeight() * (i / 2), paint);
            }
        } else {
            Log.e("MergeError", "Couldn't merge bitmaps");
        }
        return result;
    }
}
