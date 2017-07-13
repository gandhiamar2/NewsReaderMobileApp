package com.example.gandh.inclass04;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import static android.R.color.holo_blue_bright;

public class MainActivity extends AppCompatActivity implements second.intf, fourth.setimage{

    Button getnews, finish;
    Spinner sp;
    int posi=0;
    ImageButton i1,i2,i3,i4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i1 = (ImageButton) findViewById(R.id.first);
        i2 = (ImageButton) findViewById(R.id.prev);
        i3 = (ImageButton) findViewById(R.id.next);
        i4 = (ImageButton) findViewById(R.id.last);
        i1.setEnabled(false);
        i2.setEnabled(false);

        i3.setEnabled(false);i4.setEnabled(false);

            getnews = (Button) findViewById(R.id.getnews);
            finish = (Button) findViewById(R.id.finish);
        finish.setEnabled(false);
            sp = (Spinner) findViewById(R.id.spinner);
            getnews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i1.setEnabled(true);
                    i2.setEnabled(true);
                    i3.setEnabled(true);
                    i4.setEnabled(true);
                    finish.setEnabled(true);
                  //  finish.setBackgroundTintList(ColorStateList.valueOf(Color.blue()) );
                    if (checknet()) {
                        int id = sp.getSelectedItemPosition();
                        if(id !=0) {
                            String newslist[] = {"select", "bbc-news", "cnn", "buzzfeed", "espn", "sky-news"};
                            String encodedurl = urlencoder(newslist[id]);
                            Log.d("demo", encodedurl);
                            new second(MainActivity.this).execute(encodedurl);
                        }
                        else
                        {Toast.makeText(MainActivity.this,"select news source",Toast.LENGTH_SHORT).show();}
                    }
                    else {
                        Toast.makeText(MainActivity.this, "no internt connection", Toast.LENGTH_LONG).show();
                    }
                }
            });

        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public boolean checknet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nw = cm.getActiveNetworkInfo();
        if(nw!= null && nw.isConnected())
        {return true;

        }
        else
            return false;
    }

    public String urlencoder(String s){

        return "https://newsapi.org/v1/articles?apiKey=af2db6e7fba0443fbaff8043b4cfe034&source="+s;
    }


    @Override
    public void setdata(final ArrayList<newsarticle> news) {

        posi=0;
        positionsetter(posi,news);


        findViewById(R.id.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posi = 0;
                positionsetter(posi,news);
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posi !=0)
                    posi--;

                positionsetter(posi,news);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posi < news.size()-1)
                    posi++;

                positionsetter(posi,news);
            }
        });

        findViewById(R.id.last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posi = news.size()-1;
                positionsetter(posi,news);
            }
        });

    }

    public void positionsetter(int i, ArrayList<newsarticle> news1)
    {
        LinearLayout ll =   (LinearLayout) findViewById(R.id.ll);
        ll.removeAllViews();
        new fourth(MainActivity.this).execute(news1.get(i).getUrlToImage());
       // LinearLayout l1 = new LinearLayout(this);
        ImageView iv = (ImageView) findViewById(R.id.imageView) ;
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);
        t1.setText("News title: "+news1.get(i).getTitle());
        t2.setText("Author: "+news1.get(i).getAuthor());
        String date = news1.get(i).getPublishedAt();
        if(date.contains("null"))
        {
            t3.setText("Published on: "+news1.get(i).getPublishedAt());
        }
        else
        t3.setText("Published on: "+news1.get(i).getPublishedAt().substring(0,10));


        t4.setText("\n"+"\n"+"Description: "+"\n"+news1.get(i).getDescription());
        ll.addView(t1);
        ll.addView(t2);
        ll.addView(t3);
        ll.addView(t4);

    }

    @Override
    public void settingimage(Bitmap bit) {

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(bit);
    }
}
