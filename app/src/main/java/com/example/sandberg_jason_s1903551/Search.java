package com.example.sandberg_jason_s1903551;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search extends AppCompatActivity {

    private Button backButton;
    private final OkHttpClient client = new  OkHttpClient();
    ArrayList<trafficItem> arrayList1 = new ArrayList<>();
    ListView search_list;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList2 = new ArrayList<>();
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Task3();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        while(!finished) {

            Log.e("not finished", "not finished");
        }
        if (finished) {
            for (int i = 0; i < arrayList1.size(); i++) {
                System.out.println("array1 list: " + arrayList1.size());
                System.out.println("array2 list: " + arrayList2.size());
                String descriptions[] = arrayList1.get(i).getDescription().split("<br />");
                arrayList2.add(arrayList1.get(i).getTitle() + "\n" + descriptions[0] + "\n" + descriptions[1] + "\n" + descriptions[2]);
            }


        }

        search_list = (ListView) findViewById(R.id.search_list);

        adapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, arrayList2);
        search_list.setAdapter(adapter);

        backButton = findViewById(R.id.backButtonSearch);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), Viewselector.class);
                startActivity(switchActivityIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_list);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    public void Task3()
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


                                    arrayList1.add(item);
                                }

                                eventType = xpp.next();
                            }

                    finished = true;


                        } catch (XmlPullParserException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
        }

    }
