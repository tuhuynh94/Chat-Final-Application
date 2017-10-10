package com.thuong.tu.chatapplication.yolo.utils;


import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class uService  {
    public static String m_host = "http://192.168.0.105/";
    public static String m_sign_in_link = "chat/login.php";
    public static class request extends AsyncTask<Object, Void, String>{

        @Override
        protected String doInBackground(Object...params) {
            String link = params[1].toString();
            try {
                String query = ((Uri.Builder)params[0]).build().getEncodedQuery();
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( query );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String test = result;
        }
    }
}
