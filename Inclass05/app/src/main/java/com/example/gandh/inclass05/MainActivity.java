package com.example.gandh.inclass05;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements task.intf, Image.setimage {
    Button news,prev, next, first, last, finish;
    int posi=0;
    ProgressBar pb;
    LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.pb1);
        pb.setVisibility(pb.INVISIBLE);
        if(checkinternet()) {
            findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            findViewById(R.id.news).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo","here");
                    pb.setVisibility(pb.VISIBLE);
                new task(MainActivity.this).execute( "http://rss.cnn.com/rss/cnn_tech.rss");
                    Log.d("demo","here");
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this,"no internet",Toast.LENGTH_SHORT);
        }
    }

    public Boolean checkinternet()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw = cm.getActiveNetworkInfo();
        if(nw!= null&& nw.isConnected())
        {return true;
        }
        else
            return false;
    }


    public void newsgen(final ArrayList<Channel> ch) {
        posi=0;
        positionsetter(posi,ch);


        findViewById(R.id.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posi = 0;
                positionsetter(posi,ch);
            }
        });

        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posi !=0)
                    posi--;

                positionsetter(posi,ch);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posi < ch.size()-1)
                    posi++;

                positionsetter(posi,ch);
            }
        });

        findViewById(R.id.last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posi = ch.size()-1;
                positionsetter(posi,ch);
            }
        });
    }

    private void positionsetter(int posi, ArrayList<Channel> ch) {
        pb.setVisibility(pb.INVISIBLE);

l1 = (LinearLayout) findViewById(R.id.ll1);
        l1.removeAllViews();
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        new Image(MainActivity.this).execute(ch.get(posi).getImageurl());
        Log.d("demo",ch.get(posi).getTitle()+"");
        t1.setText("News title:"+ch.get(posi).getTitle());
        t2.setText("Published date:"+ch.get(posi).getPubdate());
        t3.setText("description:"+ch.get(posi).getDesc());
        l1.addView(t1);
        l1.addView(t2);
        l1.addView(t3);

    }

    @Override
    public void settingimage(Bitmap bit) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(bit);
    }
}
