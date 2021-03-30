package com.example.cs4084_mobile_application_development;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Initialise View
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        //Initialise Map Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When Map is loaded
                mMap = googleMap;
                ArrayList<LatLng> locationPoints = LocationService.getLocationPoints();
                if(locationPoints.size() != 0) {
                    mMap.addMarker(new MarkerOptions()
                            .position(locationPoints.get(0))
                            .title("Start"));
                    mMap.addMarker(new MarkerOptions()
                            .position(locationPoints.get(locationPoints.size() - 1))
                            .title("End"));
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .addAll(locationPoints)
                            .width(7)
                            .color(Color.RED));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoints.get(locationPoints.size() - 1), 15));
                }
            }
        });

        return view;
    }
}
