package com.example.saeed_ayishatu_s2110987mpd.Activities;
//Name: Ayishatu Saeed
//Student ID:S2110987

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.saeed_ayishatu_s2110987mpd.R;

public class AboutActivity extends AppCompatActivity {

    private TextView appNameTextView;
    private TextView appVersionTextView;
    private TextView developerTextView;
    private Button privacyPolicyButton;
    private Button termsOfServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appNameTextView = findViewById(R.id.appNameTextView);
        appVersionTextView = findViewById(R.id.appVersionTextView);
        developerTextView = findViewById(R.id.developerTextView);
        privacyPolicyButton = findViewById(R.id.privacyPolicyButton);
        termsOfServiceButton = findViewById(R.id.termsOfServiceButton);

        // Set the app information
        appNameTextView.setText("Weather App");
        appVersionTextView.setText("Version 1.0");
        developerTextView.setText("Developed by Saeed Ayishatu");

        // Set up the privacy policy and terms of service buttons
        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrivacyPolicyDialog();
            }
        });

        termsOfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsOfServiceDialog();
            }
        });
    }

    private void showPrivacyPolicyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Privacy Policy")
                .setMessage("This is the privacy policy of the Weather App. We take your privacy seriously and are committed to protecting your personal information.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showTermsOfServiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms of Service")
                .setMessage("These are the terms of service for using the Weather App. By using this app, you agree to abide by these terms.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape orientation
            // Update the UI or perform any necessary actions
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Portrait orientation
            // Update the UI or perform any necessary actions
        }
    }
}