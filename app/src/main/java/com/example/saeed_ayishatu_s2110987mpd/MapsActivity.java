package com.example.saeed_ayishatu_s2110987mpd;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.saeed_ayishatu_s2110987mpd.Adapters.CustomInfoWindowAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.saeed_ayishatu_s2110987mpd.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
            mMap.addMarker(new MarkerOptions()
                    .position(locationLatLng)
                    .title(locationName));
        }

        // Move the camera to a default position or the first location
        if (!locationMap.isEmpty()) {
            LatLng firstLocationLatLng = getLatLngForLocationId(locationMap.values().iterator().next());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocationLatLng, 10));
        }
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