package com.example.cs4084_mobile_application_development;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapFragment extends Fragment {

    private GoogleMap mMap;
    private ObservableArrayList<LatLng> locationPoints;
    private Marker startPoint;
    private Marker endPoint;
    private Polyline line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Get the reference to location points
        locationPoints = LocationService.getLocationPoints();
        // Add a listener so we can update the map when a point is added to the array
        locationPoints.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<LatLng>>() {
            @Override
            public void onChanged(ObservableList<LatLng> sender) {
            }

            @Override
            public void onItemRangeChanged(ObservableList<LatLng> sender, int positionStart, int itemCount) {
            }

            @Override
            public void onItemRangeInserted(ObservableList<LatLng> sender, int positionStart, int itemCount) {
                // When a point is inserted, remove all existing drawings on the map
                if (line != null) {
                    line.setPoints(sender);
                    startPoint.remove();
                    endPoint.remove();
                }
                // Then, redraw the map
                setupMap();
            }

            @Override
            public void onItemRangeMoved(ObservableList<LatLng> sender, int fromPosition, int toPosition, int itemCount) {
            }

            @Override
            public void onItemRangeRemoved(ObservableList<LatLng> sender, int positionStart, int itemCount) {
            }
        });

        //Initialise View
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Initialise Map Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When Map is loaded
                mMap = googleMap;
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_theme));
                setupMap();
            }
        });

        return view;
    }

    private void setupMap() {
        if (!locationPoints.isEmpty()) {
            if (locationPoints.size() == 1) {
                startPoint = mMap.addMarker(new MarkerOptions()
                        .position(locationPoints.get(locationPoints.size() - 1))
                        .title("Current Position"));
                endPoint = startPoint;
            } else {
                startPoint = mMap.addMarker(new MarkerOptions()
                        .position(locationPoints.get(0))
                        .title("Start"));

                endPoint = mMap.addMarker(new MarkerOptions()
                        .position(locationPoints.get(locationPoints.size() - 1))
                        .title("End"));
            }
            line = mMap.addPolyline(new PolylineOptions()
                    .addAll(locationPoints)
                    .width(7)
                    .color(Color.RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoints.get(locationPoints.size() - 1), 18));
        }
    }
}
