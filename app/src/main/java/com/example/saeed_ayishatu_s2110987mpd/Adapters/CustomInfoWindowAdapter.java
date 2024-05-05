package com.example.saeed_ayishatu_s2110987mpd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.saeed_ayishatu_s2110987mpd.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // You can return null to use the default info window frame
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Bind your observation data to the views in your custom info window layout
        TextView observationData = mWindow.findViewById(R.id.observation_data);
        observationData.setText("Observation Data for " + marker.getTitle());

        // Return the view
        return mWindow;
    }
}
