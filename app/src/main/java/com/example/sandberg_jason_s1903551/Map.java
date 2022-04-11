package com.example.sandberg_jason_s1903551;

import static com.google.android.gms.maps.MapsInitializer.initialize;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private final OkHttpClient client = new  OkHttpClient();
    LinkedList<trafficItem> linkedlist = new LinkedList<>();
    ArrayList<LatLng> positions = new ArrayList<>();
    private Button backButton;
    GoogleMap mapApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Task();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map);


        backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), Viewselector.class);
                startActivity(switchActivityIntent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }



        public void Task()
        {

            Request request = new Request.Builder()
                    .url("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx")
                    .build();

            client.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d("on response", "onResponse: " + Thread.currentThread().getId());

                    if (response.isSuccessful()) {

                Log.e("anything", "anything");
                        try {


                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(false);
                            XmlPullParser xpp = factory.newPullParser();
                            xpp.setInput(response.body().byteStream(),"UTF-8");
                            int eventType = xpp.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {


                                if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                                    trafficItem item = new trafficItem();
                                    while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equals("item"))) {

                                        if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("title")) {
                                            item.setTitle(xpp.nextText());

                                        } else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("description")) {
                                            item.setDescription(xpp.nextText());

                                        } else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("link")) {
                                            item.setLink(xpp.nextText());

                                        } else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("georss:point")) {
                                            item.setGeorsspoint(xpp.nextText());


                                        } else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("pubDate")) {
                                            item.setPubdate(xpp.nextText());

                                        }

                                        eventType = xpp.next();
                                    }


                                    linkedlist.add(item);
                                }

                                eventType = xpp.next();
                            }

                            Log.e("Display markers", "Displaying Markers");

                            Log.e("Displayed", "markers displayed");


                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            });

        }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mapApi = googleMap;
        for (int i = 0; i < linkedlist.size(); i++) {
            String coordinates = linkedlist.get(i).getGeorsspoint();

            String[] splitcoordsarray = coordinates.split(" ");


            double latitude = Double.parseDouble(splitcoordsarray[0]);
            double longitude = Double.parseDouble(splitcoordsarray[1]);
            LatLng position = new LatLng(latitude, longitude);

            positions.add(position);
            Log.e("print array", positions.toString());

              Marker marker = mapApi.addMarker(new MarkerOptions()
                        .position(positions.get(i))
                        .title(linkedlist.get(i).getTitle()));

            CameraUpdate CU = CameraUpdateFactory.newLatLngZoom(position,7);
            mapApi.animateCamera(CU);
            mapApi.setOnMarkerClickListener(this);

        }
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        marker.showInfoWindow();

        return false;
    }

}