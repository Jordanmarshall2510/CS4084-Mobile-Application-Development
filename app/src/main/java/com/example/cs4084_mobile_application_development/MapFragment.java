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
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(
                                new LatLng(52.6738, -8.5541),
                                new LatLng(52.6741, -8.5544),
                                new LatLng(52.6730, -8.5550),
                                new LatLng(52.6760, -8.5570)
                        )
                        .width(7)
                        .color(Color.RED));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.6738, -8.5541), 15));
            }
        });

        return view;
    }
}
