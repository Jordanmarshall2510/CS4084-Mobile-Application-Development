package com.example.cs4084_mobile_application_development;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import androidx.databinding.ObservableArrayList;

public class LocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.cs4084_mobile_application_development.UPDATE_LOCATION";
    private static ObservableArrayList<LatLng> locationPoints = new ObservableArrayList<LatLng>();

    @Override
    /**
     * Listens for location updates
     * @param context The context of the application
     * @param intent The operation to be performed
     * @return The stuff it returns
     */
    public void onReceive(Context context, Intent intent) {
        // Check if the intent is empty
        if (intent != null) {
            // Get the operation from the intent
            final String action = intent.getAction();
            // Check if the operation is a location update
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                // Get the location from the intent
                LocationResult result = LocationResult.extractResult(intent);
                // Ensure the location is not empty
                if (result != null) {
                    // Convert the location to a LatLng point
                    Location location = result.getLastLocation();
                    LatLng locationPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    // Clear previous points if we are not currently tracking routes
                    // This allows us to display the current location while not tracking a route
                    if(!stopStart.isStarted()) {
                        resetLocationPoints();
                    }
                    // Add the location to the route
                    locationPoints.add(locationPoint);
                }
            }
        }
    }

    /**
     * Clears the contents of the locationPoints array
     */
    public static void resetLocationPoints() {
        locationPoints.clear();
    }

    /**
     * Get the locationPoints array
     * @return The reference to the locationPoints array
     */
    public static ObservableArrayList<LatLng> getLocationPoints() {
        return locationPoints;
    }
}