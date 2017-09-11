package com.example.sdge_app;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide the top bar because its nto centered
        getSupportActionBar().hide();

        //underline the title
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Find the View that shows the map
        TextView map = (TextView) findViewById(R.id.map);
        // Set a click listener on that View
        map.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent mapIntent = new Intent(MainActivity.this, map_zones.class);
                // Start the new activity
                startActivity(mapIntent);
            }
        });

        // Find the View that shows the map
        TextView recents = (TextView) findViewById(R.id.recents);
        // Set a click listener on that View
        recents.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent recentIntent = new Intent(MainActivity.this, recentLocations.class);
                // Start the new activity
                startActivity(recentIntent);
            }
        });

        // Find the View that shows the map
        TextView aboutPage = (TextView) findViewById(R.id.aboutPage);
        // Set a click listener on that View
        aboutPage.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent aboutIntent = new Intent(MainActivity.this, aboutPage.class);
                // Start the new activity
                startActivity(aboutIntent);
            }
        });

        final TextView aboutsdff = (TextView) findViewById(R.id.aboutSDFF);
        aboutsdff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, futureCodes.class);
                startActivity(intent);
            }
        });

        // Find the View that shows the map
        TextView helpPage = (TextView) findViewById(R.id.helpPage);
        // Set a click listener on that View
        helpPage.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link NumbersActivity}
                Intent helpIntent = new Intent(MainActivity.this, helpPage.class);
                // Start the new activity
                startActivity(helpIntent);
            }
        });
    }
}
