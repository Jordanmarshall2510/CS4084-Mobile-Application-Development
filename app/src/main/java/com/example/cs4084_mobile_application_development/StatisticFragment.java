package com.example.cs4084_mobile_application_development;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public class StatisticFragment extends Fragment {

    private ObservableArrayList<LatLng> locationPoints;

    double distance = 100;
    double time = 200;
    double speed = 300;
    double calories = 400;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistic,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View currentView = getView();
        locationPoints = LocationService.getLocationPoints();

        // Get references to text views we want to change
        TextView distanceTextStatistic = (TextView) currentView.findViewById(R.id.distanceTextStatistic);
        TextView timeTextStatistic = (TextView) currentView.findViewById(R.id.timeTextStatistic);
        TextView speedTextStatistic = (TextView) currentView.findViewById(R.id.speedTextStatistic);
        TextView caloriesBurntTextStatistic = (TextView) currentView.findViewById(R.id.caloriesBurntTextStatistic);

        // Change the text
        distanceTextStatistic
                .setText("Distance:    " + distance + "km");
        timeTextStatistic
                .setText("Time:           " + time + "");
        speedTextStatistic
                .setText("Speed:         " + speed + "");
        caloriesBurntTextStatistic
                .setText("Calories:      " + calories + "kcal");

        System.out.println("##################################################    TotalDistance: " + calculateTotalDistance(locationPoints));

    }

    public static double distance(LatLng one, LatLng two, char unit) {
        double theta = one.longitude - two.longitude;
        double dist = Math.sin(deg2rad(one.latitude)) * Math.sin(deg2rad(two.latitude)) + Math.cos(deg2rad(one.latitude)) * Math.cos(deg2rad(two.latitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    //  This function converts decimal degrees to radians
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //  This function converts radians to decimal degrees
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public double calculateTotalDistance(ObservableArrayList<LatLng> points)
    {
        double result = 0;

        for (int i = 1; i < points.size(); i++)
        {
            result += distance(points.get(i), points.get(i-1), 'K');
        }

        return result;
    }
}


