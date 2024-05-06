package com.example.saeed_ayishatu_s2110987mpd;
//Name: Ayishatu Saeed
//Student ID:S2110987
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saeed_ayishatu_s2110987mpd.Adapters.ForecastDataAdapter;
import com.example.saeed_ayishatu_s2110987mpd.Domains.ForecastData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewForecast;
    private ForecastDataAdapter forecastDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Set up the forecast recycler view
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast);
        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(this));
        forecastDataAdapter = new ForecastDataAdapter(new ArrayList<>());
        recyclerViewForecast.setAdapter(forecastDataAdapter);

        // Fetch the forecast data
        fetchForecastData();

    }

    private void fetchForecastData() {
        String observationUrl = "observationUrl";
        String forecastUrl = "forecastUrl";
        new FetchWeatherDataTask().execute(observationUrl, forecastUrl);
    }

    private class FetchWeatherDataTask extends AsyncTask<String, Void, Pair<ObservationData, List<ForecastData>>> {
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

            if (forecastDataList != null) {
                forecastDataAdapter.setForecastDataList(forecastDataList);
                forecastDataAdapter.notifyDataSetChanged();
            } else {
                Log.e("FetchWeatherDataTask", "Forecast data is null or empty");
            }
        }
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