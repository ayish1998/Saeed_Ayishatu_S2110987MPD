package com.example.saeed_ayishatu_s2110987mpd;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.saeed_ayishatu_s2110987mpd.Adapters.ForecastDataAdapter;
import com.example.saeed_ayishatu_s2110987mpd.Domains.ForecastData;

/** @noinspection ALL*/
public class FetchWeatherDataTask extends AsyncTask<String, Void, Pair<ObservationData, List<ForecastData>>> {

    private ForecastDataAdapter forecastDataAdapter;


    public FetchWeatherDataTask(ForecastDataAdapter forecastDataAdapter) {
        this.forecastDataAdapter = forecastDataAdapter;
    }

    private TextView textViewTemperature;
    private TextView textViewVisibility;
    private TextView textViewPressure;
    private TextView textViewHumidity;
    private TextView textViewWindDirection;
    private TextView textViewWindSpeed;
    private TextView textViewWeatherCondition;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewForecastDay1;
    private TextView textViewForecastDay2;
    private ImageView imageViewWeatherIcon;


    public FetchWeatherDataTask(TextView textViewTemperature, TextView textViewVisibility, TextView textViewPressure, TextView textViewHumidity, TextView textViewWindDirection, TextView textViewWindSpeed, TextView textViewWeatherCondition, TextView textViewDate, TextView textViewTime, ImageView imageViewWeatherIcon) {
        this.textViewTemperature = textViewTemperature;
        this.textViewVisibility = textViewVisibility;
        this.textViewPressure = textViewPressure;
        this.textViewHumidity = textViewHumidity;
        this.textViewWindDirection = textViewWindDirection;
        this.textViewWindSpeed = textViewWindSpeed;
        this.textViewWeatherCondition = textViewWeatherCondition;
        this.textViewDate = textViewDate;
        this.textViewTime = textViewTime;
        this.imageViewWeatherIcon = imageViewWeatherIcon;
    }

    public FetchWeatherDataTask() {

    }



    @Override
    protected Pair<ObservationData, List<ForecastData>> doInBackground(String... urls) {
        ObservationData observationData = null;
        List<ForecastData> forecastDataList = null;
        try {
            observationData = WeatherDataParser.fetchAndParseObservationData(urls[0]);
            forecastDataList = WeatherDataParser.fetchAndParseForecastData(urls[1]);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions, e.g., network errors
        }
        return new Pair<>(observationData, forecastDataList);
    }

    @Override
    protected void onPostExecute(Pair<ObservationData, List<ForecastData>> result) {
        ObservationData observationData = result.first;
        List<ForecastData> forecastDataList = result.second;

        if (forecastDataAdapter != null && forecastDataList != null) {
            forecastDataAdapter.setForecastDataList(forecastDataList);
            forecastDataAdapter.notifyDataSetChanged();
//            Log.d("FetchWeatherDataTask", "Forecast data updated in adapter");
        }
        if (observationData != null) {

        if (imageViewWeatherIcon != null) {
            String weatherCondition = observationData.getWeatherCondition();
            int weatherIconResId = getWeatherIconResId(weatherCondition);
            imageViewWeatherIcon.setImageResource(weatherIconResId);
        }

        // Update the TextViews with the observation data

            if (textViewTemperature != null) {
                textViewTemperature.setText(observationData.getTemperature());
            }
            if (textViewVisibility != null) {
                textViewVisibility.setText(observationData.getVisibility());
            }
            if (textViewPressure != null) {
                textViewPressure.setText(observationData.getPressure());
            }
            if (textViewHumidity != null) {
                textViewHumidity.setText(observationData.getHumidity());
            }
            if (textViewWindDirection != null) {
                textViewWindDirection.setText(observationData.getWindDirection());
            }
            if (textViewWindSpeed != null) {
                textViewWindSpeed.setText(observationData.getWindSpeed());
            }
            if (textViewWeatherCondition != null) {
                textViewWeatherCondition.setText(observationData.getWeatherCondition());
            }
            if (textViewDate != null) {
                textViewDate.setText(observationData.getDate());
            }
            if (textViewTime != null) {
                textViewTime.setText(observationData.getTime());
            }

        } else {
            // Handle the case where observationData is null
            Log.e("FetchWeatherDataTask", "ObservationData is null");
        }

        // Update TextViews with forecast data
        if (forecastDataList != null && !forecastDataList.isEmpty()) {
            StringBuilder forecastText = new StringBuilder();

            for (int i = 0; i < forecastDataList.size(); i++) {
                ForecastData forecastData = forecastDataList.get(i);
                forecastText.append("Day ").append(i + 1).append(": ").append(forecastData.getDay()).append(", Weather Condition: ").append(forecastData.getWeatherCondition()).append("\n");
                // Update UV Index and Dewpoint if available
                if (forecastData.getUvRisk() != null) {
                    // Update UI with UV Index
                }
                if (forecastData.getDewpoint() != null) {
                    // Update UI with Dewpoint
                }
            }

            // Assuming you have a single TextView to display all forecast data
            if (textViewForecastDay1 != null) {
                textViewForecastDay1.setText(forecastText.toString());
            }
        } else {
            // Handle the case where forecastDataList is null or empty
            Log.e("FetchWeatherDataTask", "Forecast data is null or empty");
        }

    }

    private int getWeatherIconResId(String weatherCondition) {
        // Check if weatherCondition is null
        if (weatherCondition == null) {
            // Return a default icon resource ID or handle the null case appropriately
            return R.drawable.rain_thunder;
        }

        // Map weather condition to drawable resource ID
        if (weatherCondition.equals("Sunny")) {
            return R.drawable.sunny;
        } else if (weatherCondition.equals("Rainy")) {
            return R.drawable.rainy;
        } else if (weatherCondition.equals("Cloudy")) {
            return R.drawable.cloudyy;
        } else {
            // Default icon for unknown weather conditions
            return R.drawable.day_rain;
        }
    }
}
