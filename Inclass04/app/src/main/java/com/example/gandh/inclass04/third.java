package com.example.gandh.inclass04;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gandh on 2/6/2017.
 */

public class third {

    public static ArrayList<newsarticle> parsingjson(String s) throws JSONException {
        ArrayList<newsarticle> news = new ArrayList<newsarticle>();
        JSONObject sonobject = new JSONObject(s);
        JSONArray sonArray = sonobject.getJSONArray("articles");
        newsarticle nw = null;
        for (int i = 0; i < sonArray.length(); i++) {
            JSONObject subobject = sonArray.getJSONObject(i);
            nw = new newsarticle();
            nw.setAuthor(subobject.getString("author"));
            nw.setTitle(subobject.getString("title"));
            nw.setDescription(subobject.getString("description"));
            nw.setUrlToImage(subobject.getString("urlToImage"));
            nw.setPublishedAt(subobject.getString("publishedAt"));
            news.add(nw);

        }
        return news;
    }
}
