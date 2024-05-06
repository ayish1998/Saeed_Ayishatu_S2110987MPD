package com.example.saeed_ayishatu_s2110987mpd;
//Name: Ayishatu Saeed
//Student ID:S2110987
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, OnboardingScreen.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // Delay of 3 seconds
    }
}
