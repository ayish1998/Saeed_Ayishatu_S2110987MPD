package com.example.saeed_ayishatu_s2110987mpd;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.saeed_ayishatu_s2110987mpd.Adapters.CustomInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.saeed_ayishatu_s2110987mpd.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Map<String, ObservationData> observationDataMap = new HashMap<>();
    private Map<String, Marker> markerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_with_back_button);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Set up the search button click listener
        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation();
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                // Handle the back button click
                onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Example locations and their IDs
        Map<String, Integer> locationMap = new HashMap<>();
        locationMap.put("Glasgow", 2648579);
        locationMap.put("London", 2643743);
        locationMap.put("New York", 5128581);
        locationMap.put("Oman", 287286);
        locationMap.put("Port Louis, Mauritius", 934154);
        locationMap.put("Bangladesh", 1185241);

        // Iterate over the location map and add markers for each location
        for (Map.Entry<String, Integer> entry : locationMap.entrySet()) {
            String locationName = entry.getKey();
            int locationId = entry.getValue();

            // Get the LatLng for the location ID
            LatLng locationLatLng = getLatLngForLocationId(locationId);

            // Add a marker for the location
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(locationLatLng)
                    .title(locationName));

            // Store the marker in the markerMap
            markerMap.put(locationName, marker);

            // Retrieve the observation data for the location
            ObservationData observationData = getObservationDataForLocation(locationName);
            observationDataMap.put(locationName, observationData);
        }

        // Move the camera to a default position or the first location
        if (!locationMap.isEmpty()) {
            LatLng firstLocationLatLng = getLatLngForLocationId(locationMap.values().iterator().next());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocationLatLng, 10));
        }

        // Set a custom info window adapter
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this, observationDataMap));
    }

    private void searchLocation() {
        EditText searchEditText = findViewById(R.id.search_edit_text);
        String searchQuery = searchEditText.getText().toString().trim();

        // Check if the search query matches any of the location names
        if (markerMap.containsKey(searchQuery)) {
            Marker marker = markerMap.get(searchQuery);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10));
            marker.showInfoWindow();
        } else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }
    }

    private ObservationData getObservationDataForLocation(String locationName) {
        // Retrieve the observation data for the given location name
        // You can make an API call or fetch the data from a local database
        // For simplicity, let's assume you have a method that returns the observation data
        return fetchObservationDataFromAPI(locationName);
    }

    private ObservationData fetchObservationDataFromAPI(String locationName) {
        // Simulated method to fetch observation data from an API
        // Replace this with your actual implementation
        ObservationData observationData = new ObservationData();
        observationData.setWeatherCondition("Sunny");
        observationData.setTemperature("25°C");
        observationData.setWindDirection("NW");
        observationData.setWindSpeed("10 km/h");
        observationData.setHumidity("60%");
        observationData.setPressure("1013 hPa");
        observationData.setVisibility("10 km");
        observationData.setDate("2023-06-07");
        observationData.setTime("14:30");
        observationData.setCountryName(locationName);
        return observationData;
    }

    private LatLng getLatLngForLocationId(int locationId) {
        // Manually map location IDs to LatLng objects
        Map<Integer, LatLng> locationIdToLatLngMap = new HashMap<>();
        locationIdToLatLngMap.put(2648579, new LatLng(55.8642, -4.2518)); // Glasgow
        locationIdToLatLngMap.put(2643743, new LatLng(51.5074, -0.1278)); // London
        locationIdToLatLngMap.put(5128581, new LatLng(40.7128, -74.0060)); // New York
        locationIdToLatLngMap.put(287286, new LatLng(23.5888, 58.3852)); // Oman
        locationIdToLatLngMap.put(934154, new LatLng(-20.2855, 57.4782)); // Port Louis, Mauritius
        locationIdToLatLngMap.put(1185241, new LatLng(23.6850, 90.3563)); // Bangladesh

        // Return the LatLng for the given location ID, or a default LatLng if not found
        return locationIdToLatLngMap.getOrDefault(locationId, new LatLng(0, 0));
    }
}