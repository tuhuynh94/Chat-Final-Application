package com.thuong.tu.chatapplication.yolo.frontend.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by gardo on 08/12/2017.
 */

public class ProcessDialogHelper {
    public static ProgressDialog createProcessDialog(Context context, String messages){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(messages);
        return progressDialog;
    }
}
