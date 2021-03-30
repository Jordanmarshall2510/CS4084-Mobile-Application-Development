package com.example.cs4084_mobile_application_development;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.cs4084_mobile_application_development.UPDATE_LOCATION";
    private static ArrayList<LatLng> locationPoints = new ArrayList<LatLng>();

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent != null){
            final String action = intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);
                if(result != null){
                    Location location = result.getLastLocation();
                    LatLng locationPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    locationPoints.add(locationPoint);
                }
            }
        }
    }

    public static ArrayList<LatLng> getLocationPoints(){
        return locationPoints;
    }
}