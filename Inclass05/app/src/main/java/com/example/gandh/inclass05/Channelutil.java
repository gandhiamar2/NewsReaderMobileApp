package com.example.gandh.inclass05;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by gandh on 2/13/2017.
 */

public class Channelutil {

    static class channelparsin {

        public static ArrayList<Channel> parser(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser pull = XmlPullParserFactory.newInstance().newPullParser();
            pull.setInput(in, "UTF-8");
            ArrayList<Channel> channelarray = new ArrayList<>();
            Channel ch = new Channel();
            int count = 0;
            int event = pull.getEventType();
            int i = 0;
            while (event != XmlPullParser.END_DOCUMENT) {
                Log.d("test","in while loop-----------");
                switch (event) {
                    case XmlPullParser.START_TAG: {
                        Log.d("test","in while loop start-----------");
                        if (pull.getName().equals("channel")) {
                            channelarray = new ArrayList<>();
                        } else if (pull.getName().equals("item")) {


                            count = 1;
                        } else if (pull.getName().equals("title")) {

                        }
                        if (count == 1) {
                            if (pull.getName().equals("title")) {
                                ch = new Channel();
                                ch.setTitle(pull.nextText());
                            } else if (pull.getName().equals("description")) {
                                ch.setDesc(pull.nextText());
                            } else if (pull.getName().equals("pubDate")) {
                                ch.setPubdate(pull.nextText());
                            } else if (pull.getName().equals("media:content")) {
                                if (pull.getAttributeValue(null, "height").equals("300") && pull.getAttributeValue(null, "width").equals("300"))
                                    ch.setImageurl(pull.getAttributeValue(null, "url"));
                            }
                        }

                    }
                    break;

                    case XmlPullParser.END_TAG: {
                        if (pull.getName().equals("item")) {
                            if (ch != null) {
                                channelarray.add(ch);
                                Log.d("test",ch.getTitle());


                                ch = null;
                            }


                        }
                        break;
                    }

                }
                event = pull.next();

            }
            for (Channel c  :channelarray
                 ) {
                Log.d("date",c.getPubdate());
                Log.d("desc",c.getDesc());
                Log.d("desc",channelarray.size()+"");

            }
            return channelarray;
        }

    }
}
