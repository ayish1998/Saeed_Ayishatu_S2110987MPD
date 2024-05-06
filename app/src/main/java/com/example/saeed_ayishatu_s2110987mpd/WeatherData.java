package com.example.saeed_ayishatu_s2110987mpd;
//Name: Ayishatu Saeed
//Student ID:S2110987
import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

public class WeatherData {
    private static final String BASE_URL_OBSERVATION = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/";
    private static final String BASE_URL_FORECAST = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/";



    @NonNull
    @Contract(pure = true)
    public static String getObservationUrl(int locationId) {
        return BASE_URL_OBSERVATION + locationId;
    }

    @NonNull
    @Contract(pure = true)
    public static String getForecastUrl(int locationId) {
        return BASE_URL_FORECAST + locationId;
    }
}

