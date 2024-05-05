package com.example.saeed_ayishatu_s2110987mpd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.saeed_ayishatu_s2110987mpd.Activities.AboutActivity;
import com.example.saeed_ayishatu_s2110987mpd.Activities.SettingsActivity;
import com.example.saeed_ayishatu_s2110987mpd.Adapters.ForecastDataAdapter;

public class LocationDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Removing the default title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Drawable overflowIcon = toolbar.getOverflowIcon();
        if (overflowIcon != null) {
            overflowIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            toolbar.setOverflowIcon(overflowIcon);
        }
        toolbar.setTitleTextColor(Color.BLACK);

        String observationUrl = getIntent().getStringExtra("observationUrl");
        String forecastUrl = getIntent().getStringExtra("forecastUrl");
        // Retrieve the selected location name from the intent
        String selectedLocation = getIntent().getStringExtra("selectedLocation");

        // Set the selected location name as the text of a TextView
        TextView textViewLocationName = findViewById(R.id.textViewLocationName);
        textViewLocationName.setText(selectedLocation);

        // Initialize the RecyclerView
        RecyclerView recyclerViewForecast = findViewById(R.id.recyclerViewForecast);
        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list. The list will be populated in the FetchWeatherDataTask.
        ForecastDataAdapter forecastDataAdapter = new ForecastDataAdapter(null);
        recyclerViewForecast.setAdapter(forecastDataAdapter);

        // Pass all necessary views and the adapter to the FetchWeatherDataTask constructor
        new FetchWeatherDataTask(forecastDataAdapter).execute(observationUrl, forecastUrl);

        TextView textViewTemperature = findViewById(R.id.textViewTemperature);
        TextView textViewVisibility = findViewById(R.id.textViewVisibility);
        TextView textViewPressure = findViewById(R.id.textViewPressure);
        TextView textViewHumidity = findViewById(R.id.textViewHumidity);
        TextView textViewWindDirection = findViewById(R.id.textViewWindDirection);
        TextView textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        TextView textViewWeatherCondition = findViewById(R.id.textViewWeatherCondition);


        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewTime = findViewById(R.id.textViewTime);
        ImageView imageViewWeatherIcon = findViewById(R.id.imageViewWeatherIcon);


        // Pass all TextViews to the FetchWeatherDataTask constructor
        new FetchWeatherDataTask(textViewTemperature, textViewVisibility, textViewPressure, textViewHumidity, textViewWindDirection, textViewWindSpeed, textViewWeatherCondition, textViewDate, textViewTime, imageViewWeatherIcon).execute(observationUrl, forecastUrl);

        // Set up the "back" button click listener
        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationDataActivity.this, mainscreen.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // Handle Home item click
                        Intent homeIntent = new Intent(LocationDataActivity.this, mainscreen.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.navigation_map:
                        // Handle Map item click
                        Intent mapIntent = new Intent(LocationDataActivity.this, MapsActivity.class);
                        startActivity(mapIntent);
                        return true;
                    case R.id.navigation_about:
                        // Handle News item click
                        Intent newsIntent = new Intent(LocationDataActivity.this, AboutActivity.class);
                        startActivity(newsIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Handle settings action
            Intent intent = new Intent(LocationDataActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
