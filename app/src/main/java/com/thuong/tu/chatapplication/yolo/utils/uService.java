package com.thuong.tu.chatapplication.yolo.utils;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
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
//region other
        /*
         uService.request() request = new uService.request()
         request.setOnTaskFinishedEvent(new uService.request.OnTaskExecutionFinished() {
                    @Override
                    public void OnTaskFihishedEvent(String Result) {
                        String r = Result;
                        String a = "";
                    }
                });
          request.execute(builder, url);*/
//endregion other
    }

    private static class request extends AsyncTask<Object, Void, String> {
        //region other
/*        private OnTaskExecutionFinished _task_finished_event;

        public interface OnTaskExecutionFinished
        {
            public void OnTaskFihishedEvent(String result);
        }

        public void setOnTaskFinishedEvent(OnTaskExecutionFinished _event)
        {
            if(_event != null)
            {
                this._task_finished_event = _event;
            }
        }*/
        //endregion other
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
}


