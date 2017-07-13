package com.example.gandh.inclass05;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gandh on 2/13/2017.
 */

public class task extends AsyncTask<String,Void,ArrayList<Channel>> {

    intf activity;

    public task(intf activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Channel> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream in = con.getInputStream();

            return Channelutil.channelparsin.parser(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(ArrayList<Channel> channels) {
        super.onPostExecute(channels);
        activity.newsgen(channels);
    }

  public interface intf{

        public void newsgen(ArrayList<Channel> ch);
    }
}
