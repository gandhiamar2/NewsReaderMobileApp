package com.example.gandh.inclass04;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

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

public class second extends AsyncTask<String, Void, ArrayList<newsarticle>> {

    intf activity;

    public second(intf activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<newsarticle> doInBackground(String... params) {
        StringBuilder sb = null;
        BufferedReader bfr = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            bfr = new BufferedReader((new InputStreamReader(con.getInputStream())));
            sb = new StringBuilder();
            String s = "";
            while ((s = bfr.readLine()) != null) {
                sb.append(s);

            }
            return third.parsingjson(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                bfr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       return null;
    }

    @Override
    protected void onPostExecute(ArrayList<newsarticle> s) {
        activity.setdata(s);

    }


    public static interface intf{

        public void setdata(ArrayList<newsarticle> news);
    }

}
