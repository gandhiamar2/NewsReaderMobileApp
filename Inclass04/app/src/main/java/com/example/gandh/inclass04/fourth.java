package com.example.gandh.inclass04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gandh on 2/6/2017.
 */

public class fourth extends AsyncTask<String, Void, Bitmap> {

    setimage activity;

    public fourth(setimage activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap bit = BitmapFactory.decodeStream(con.getInputStream());
            return bit;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;




    }

    @Override
    protected void onPostExecute(Bitmap bit) {

        activity.settingimage(bit);
    }

    public interface setimage{
        public void settingimage(Bitmap bit);
    }
}
