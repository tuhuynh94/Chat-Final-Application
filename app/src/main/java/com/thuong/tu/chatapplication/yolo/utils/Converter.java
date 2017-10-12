package com.thuong.tu.chatapplication.yolo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public static Date stringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String dateToString(Date dateString) {
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        String formattedDate = targetFormat.format(dateString);
        return formattedDate;
    }
}
