package com.example.saeed_ayishatu_s2110987mpd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.saeed_ayishatu_s2110987mpd.ObservationData;
import com.example.saeed_ayishatu_s2110987mpd.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private Map<String, ObservationData> observationDataMap;

    public CustomInfoWindowAdapter(Context context, Map<String, ObservationData> observationDataMap) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
        this.observationDataMap = observationDataMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // You can return null to use the default info window frame
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Bind the observation data to the views in your custom info window layout
        TextView observationDataTextView = mWindow.findViewById(R.id.observation_data);

        String locationName = marker.getTitle();
        ObservationData observationData = observationDataMap.get(locationName);

        if (observationData != null) {
            String observationDataText = "Observation Data for " + locationName + ":\n"
                    + "Weather Condition: " + observationData.getWeatherCondition() + "\n"
                    + "Temperature: " + observationData.getTemperature() + "\n"
                    + "Wind Direction: " + observationData.getWindDirection() + "\n"
                    + "Wind Speed: " + observationData.getWindSpeed() + "\n"
                    + "Humidity: " + observationData.getHumidity() + "\n"
                    + "Pressure: " + observationData.getPressure() + "\n"
                    + "Visibility: " + observationData.getVisibility() + "\n"
                    + "Date: " + observationData.getDate() + "\n"
                    + "Time: " + observationData.getTime();

            observationDataTextView.setText(observationDataText);
        } else {
            observationDataTextView.setText("No observation data available for " + locationName);
        }

        // Return the view
        return mWindow;
    }
}