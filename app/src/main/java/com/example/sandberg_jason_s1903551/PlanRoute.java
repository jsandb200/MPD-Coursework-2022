package com.example.sandberg_jason_s1903551;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.widget.DatePicker;
import android.widget.ListView;

public class PlanRoute extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button backButton;
    private Button Bstartdate;
    private Button Benddate;
    private String startdate;
    private String enddate;
    private String descriptionDates[];
    private String filteredSD;
    private String filteredED;
    ArrayList<LocalDate> parsedFilteredStartDate;
    ArrayList<LocalDate> parsedFilteredEndDate;
    private long daysBetween;
    private Button getplan;
    private final OkHttpClient client = new OkHttpClient();
    ArrayList<trafficItem> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ListView list;
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Task2();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plan);

while(!finished) {

        Log.e("not finished", "not finished");
    }
  if (finished) {

        Log.e("Finished", "finished");

    }






        backButton = findViewById(R.id.backButtonList);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), Viewselector.class);
                startActivity(switchActivityIntent);
            }
        });


        Bstartdate = findViewById(R.id.start_date);
        Bstartdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            selectDate();



            }
        });

        Benddate = findViewById(R.id.end_date);
        Benddate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                selectDate2();



            }
        });


        getplan = findViewById(R.id.getplan);
        getplan.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(startdate != null && enddate != null) {

                    Log.e("startdate= ", startdate);
                    Log.e("enddate= ", enddate);

                    LocalDate parsedStartDate = LocalDate.parse(startdate, DateTimeFormatter.ofPattern("EEEE, LLLL d, yyyy", Locale.ENGLISH));
                    LocalDate parsedEndDate = LocalDate.parse(enddate, DateTimeFormatter.ofPattern("EEEE, LLLL d, yyyy", Locale.ENGLISH));

                    daysBetween = ChronoUnit.DAYS.between(parsedStartDate, parsedEndDate);

                    System.out.println(daysBetween);
                    System.out.println(parsedStartDate);
                    System.out.println(parsedEndDate);

                    for (int i = 0; i < arrayList1.size(); i++) {
                    descriptionDates = arrayList1.get(i).getDescription().split(" ");
                        filteredSD = descriptionDates[2] + " " + descriptionDates[4] + " " + descriptionDates[3] + ", " + descriptionDates[5];
                        filteredED = descriptionDates[10] + " " + descriptionDates[12] + " " + descriptionDates[11] + ", " + descriptionDates[13];


                        System.out.println(filteredSD);
                    //    parsedFilteredStartDate.add(LocalDate.parse(filteredSD, DateTimeFormatter.ofPattern("EEEE, LLLL d, yyyy", Locale.ENGLISH)));
                     //   parsedFilteredEndDate.add(LocalDate.parse(filteredED, DateTimeFormatter.ofPattern("EEEE, LLLL d, yyyy", Locale.ENGLISH)));

                    }


// System.out.println("PFSD "+parsedFilteredStartDate.get(0));


                }
                else{
                    Log.e("null value", "One of the values are null");
                }
            }

        });



    }


    public void selectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this::onDateSet,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();

    }

    public void selectDate2() {
        DatePickerDialog datePickerDialog2 = new DatePickerDialog(
                this,
                this::onDateSet2,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog2.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,i );
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        startdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Log.e("chosen date: ",startdate);

    }


    public void onDateSet2(DatePicker datePicker, int i, int i1, int i2) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,i );
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        enddate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Log.e("chosen date: ",enddate);

    }



    public void Task2(){


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

                        Log.e("ifSuccessful", "Success");
                        try {


                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(false);
                            XmlPullParser xpp = factory.newPullParser();
                            xpp.setInput(response.body().byteStream(), "UTF-8");
                            System.out.println(response.body().byteStream());
                            System.out.println(response.body().toString());

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



};


