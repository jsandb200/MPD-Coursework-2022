package com.example.sandberg_jason_s1903551;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements OnClickListener
{


    private Button startButton;

    private String result = "";
    private String url1="";
    // Traffic Scotland Planned Roadworks XML link
    private String
            urlSource="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");



        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");


    }




    @Override
    public void onClick(View v)
    {
        Log.e("MyTag","in onClick");

        Intent switchActivityIntent = new Intent(this, Viewselector.class);
        startActivity(switchActivityIntent);

    }















}
