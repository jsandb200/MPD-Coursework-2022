package com.example.sandberg_jason_s1903551;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Viewselector extends AppCompatActivity implements View.OnClickListener   {

    private Button mapButton;
    private Button listButton;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewselector);

        mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), Map.class);
                startActivity(switchActivityIntent);
            }
        });


        listButton =findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), PlanRoute.class);
                startActivity(switchActivityIntent);
            }
        });


        searchButton =findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent switchActivityIntent = new Intent(view.getContext(), Search.class);
                startActivity(switchActivityIntent);
            }
        });







    }


    @Override
    public void onClick(View view) {

    }
}




