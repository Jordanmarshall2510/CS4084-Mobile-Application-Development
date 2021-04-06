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

    double distance = 0;
    double time = 0;
    double speed = 0;
    double calories = 0;

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

        distance = calculateTotalDistance(locationPoints);
        time = getTimeElapsed(1609506000000L, 1609509600000L);
        speed = getSpeed(1609506000000L, 1609509600000L, calculateTotalDistance(locationPoints));
        calories = 400;

        // Get references to text views we want to change
        TextView dailyDistanceTextStatistic = (TextView) currentView.findViewById(R.id.dailyDistanceTextStatistic);
        TextView dailyTimeTextStatistic = (TextView) currentView.findViewById(R.id.dailyTimeTextStatistic);
        TextView dailySpeedTextStatistic = (TextView) currentView.findViewById(R.id.dailySpeedTextStatistic);
        TextView dailyCaloriesBurntTextStatistic = (TextView) currentView.findViewById(R.id.dailyCaloriesBurntTextStatistic);

        // Change the text
        dailyDistanceTextStatistic
                .setText("Distance:    " + distance + "km");
        dailyTimeTextStatistic
                .setText("Time:           " + time + "");
        dailySpeedTextStatistic
                .setText("Speed:         " + speed + "");
        dailyCaloriesBurntTextStatistic
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

    //Calculates total distance in meters
    public long calculateTotalDistance(ObservableArrayList<LatLng> points)
    {
        double result = 0;

        for (int i = 1; i < points.size(); i++)
        {
            result += distance(points.get(i), points.get(i-1), 'K');
        }

        return (long) (result * 1000);
    }

    //Get speed in m/s. Takes startTime and stopTime in milliseconds, and totalDistance in meters.
    public double getSpeed(long startTime, long stopTime, long totalDistance)
    {
        //timeElapsed in seconds
        double timeElapsed = (stopTime - startTime) / 1000;
        double speed = totalDistance / timeElapsed;
        speed = Math.round(speed * 100) / 100;
        return speed;
    }

    //Gets time elapsed in seconds. Takes startTime and stopTime in milliseconds.
    public long getTimeElapsed(long startTime, long stopTime)
    {
        return (stopTime - startTime) / 1000;
    }
}