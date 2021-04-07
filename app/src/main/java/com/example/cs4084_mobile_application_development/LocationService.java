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
    public void onReceive(Context context, Intent intent) {
        if(stopStart.isStarted()) {
            if (intent != null) {
                final String action = intent.getAction();
                if (ACTION_PROCESS_UPDATE.equals(action)) {
                    LocationResult result = LocationResult.extractResult(intent);
                    if (result != null) {
                        Location location = result.getLastLocation();
                        LatLng locationPoint = new LatLng(location.getLatitude(), location.getLongitude());
                        locationPoints.add(locationPoint);
                    }
                }
            }
        }
    }

    public static void resetLocationPoints() {
        locationPoints = new ObservableArrayList<LatLng>();
    }

    public static ObservableArrayList<LatLng> getLocationPoints() {
        return locationPoints;
    }
}