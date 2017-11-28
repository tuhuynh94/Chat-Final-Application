package com.thuong.tu.chatapplication.yolo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.thuong.tu.chatapplication.yolo.backend.server.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class uService  {

    public static String execute(Uri.Builder builder, String url) {
        String result = "";
        try {
            result = new uService.request().execute(builder, url).get();
        } catch (InterruptedException e) {
            e.printStackTrace(); //handle it the way you like
        } catch (ExecutionException e) {
            e.printStackTrace();//handle it the way you like
        }
        return result;
    }

    private static class request extends AsyncTask<Object, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object...params) {
            String link = params[1].toString(); //address php
            StringBuilder sb = new StringBuilder();
            try {
                String query = ((Uri.Builder)params[0]).build().getEncodedQuery(); //url query
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( query );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            } catch (Exception e) {
                Log.d("test", "Exception: " + e.getMessage());
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) { // after execute
            //region other

/*            if(this._task_finished_event != null)
            {
                this._task_finished_event.OnTaskFihishedEvent(result);
            }
            else
            {
                Log.d("SomeClass", "task_finished even is null");
            }*/
            //endregion other
        }
    }
    public JSONObject sendImage(String path)
    {
        JSONObject sendData = new JSONObject();
        try{
            sendData.put("image", encodeImage(path));
        }catch(JSONException e){
        }
        return  sendData;
    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }
    private Bitmap decodeImage(String data)
    {
        byte[] b = Base64.decode(data,Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
        return bmp;
    }
}


