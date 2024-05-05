package com.example.saeed_ayishatu_s2110987mpd.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                openWebPage("https://example.com/privacy-policy");
            }
        });

        termsOfServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://example.com/terms-of-service");
            }
        });
    }

    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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