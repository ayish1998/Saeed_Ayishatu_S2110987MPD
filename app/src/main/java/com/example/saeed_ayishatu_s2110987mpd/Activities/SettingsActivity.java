package com.example.saeed_ayishatu_s2110987mpd.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.switchmaterial.SwitchMaterial;

import com.example.saeed_ayishatu_s2110987mpd.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREF_TEMPERATURE_UNIT = "temperature_unit";
    private SwitchMaterial temperatureUnitSwitch;

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

        // Load the stored temperature unit preference
        loadTemperatureUnitPreference();

        // Set up the temperature unit switch listener
        temperatureUnitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveTemperatureUnitPreference(isChecked);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}