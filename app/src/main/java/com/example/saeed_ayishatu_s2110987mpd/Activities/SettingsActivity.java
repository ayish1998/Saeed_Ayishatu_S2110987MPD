package com.example.saeed_ayishatu_s2110987mpd.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.switchmaterial.SwitchMaterial;

import com.example.saeed_ayishatu_s2110987mpd.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREF_TEMPERATURE_UNIT = "temperature_unit";
    private static final String PREF_MORNING_UPDATE_TIME = "morning_update_time";
    private static final String PREF_EVENING_UPDATE_TIME = "evening_update_time";

    private SwitchMaterial temperatureUnitSwitch;
    private EditText morningUpdateTimeEditText;
    private EditText eveningUpdateTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        temperatureUnitSwitch = findViewById(R.id.temperatureUnitSwitch);
        morningUpdateTimeEditText = findViewById(R.id.morningUpdateTimeEditText);
        eveningUpdateTimeEditText = findViewById(R.id.eveningUpdateTimeEditText);


        // Load the stored temperature unit preference
        loadTemperatureUnitPreference();

        // Load the stored update interval preferences
        loadUpdateIntervalPreferences();

        // Set up the temperature unit switch listener
        temperatureUnitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveTemperatureUnitPreference(isChecked);
            }
        });


    // Set up the save button click listener
    Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveUpdateIntervalPreferences();
        }
    });
}

    private void loadTemperatureUnitPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        boolean isCelsius = sharedPreferences.getBoolean(PREF_TEMPERATURE_UNIT, true);
        temperatureUnitSwitch.setChecked(isCelsius);
    }

    private void saveTemperatureUnitPreference(boolean isCelsius) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_TEMPERATURE_UNIT, isCelsius);
        editor.apply();
    }

    private void loadUpdateIntervalPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        String morningUpdateTime = sharedPreferences.getString(PREF_MORNING_UPDATE_TIME, "08:00");
        String eveningUpdateTime = sharedPreferences.getString(PREF_EVENING_UPDATE_TIME, "20:00");
        morningUpdateTimeEditText.setText(morningUpdateTime);
        eveningUpdateTimeEditText.setText(eveningUpdateTime);
    }

    private void saveUpdateIntervalPreferences() {
        String morningUpdateTime = morningUpdateTimeEditText.getText().toString();
        String eveningUpdateTime = eveningUpdateTimeEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_MORNING_UPDATE_TIME, morningUpdateTime);
        editor.putString(PREF_EVENING_UPDATE_TIME, eveningUpdateTime);
        editor.apply();

        // Schedule the data updates based on the saved intervals
        scheduleDataUpdates(morningUpdateTime, eveningUpdateTime);
    }


    private void scheduleDataUpdates(String morningUpdateTime, String eveningUpdateTime) {
        // TODO: Implement the logic to schedule data updates based on the provided intervals
        // You can use libraries like AlarmManager or WorkManager to schedule the updates
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}