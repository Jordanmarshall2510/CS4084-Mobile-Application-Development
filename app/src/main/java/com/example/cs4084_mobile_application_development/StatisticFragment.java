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

        // Get references to text views we want to change
        TextView dailyDistanceTextStatistic = (TextView) currentView.findViewById(R.id.dailyDistanceTextStatistic);
        TextView dailyTimeTextStatistic = (TextView) currentView.findViewById(R.id.dailyTimeTextStatistic);
        TextView dailySpeedTextStatistic = (TextView) currentView.findViewById(R.id.dailySpeedTextStatistic);
        TextView dailyCaloriesBurntTextStatistic = (TextView) currentView.findViewById(R.id.dailyCaloriesBurntTextStatistic);

        TextView totalDistanceTextStatistic = (TextView) currentView.findViewById(R.id.totalDistanceTextStatistic);
        TextView totalTimeTextStatistic = (TextView) currentView.findViewById(R.id.totalTimeTextStatistic);
        TextView totalSpeedTextStatistic = (TextView) currentView.findViewById(R.id.totalSpeedTextStatistic);
        TextView totalCaloriesBurntTextStatistic = (TextView) currentView.findViewById(R.id.totalCaloriesBurntTextStatistic);

        // Change the daily text
        dailyDistanceTextStatistic
                .setText("Distance:    " + distance + "km");
        dailyTimeTextStatistic
                .setText("Time:           " + time + "");
        dailySpeedTextStatistic
                .setText("Speed:         " + speed + "");
        dailyCaloriesBurntTextStatistic
                .setText("Calories:      " + calories + "kcal");

        //Change the total text
        totalDistanceTextStatistic
                .setText("Distance:    " + distance + "km");
        totalTimeTextStatistic
                .setText("Time:           " + time + "");
        totalSpeedTextStatistic
                .setText("Speed:         " + speed + "");
        totalCaloriesBurntTextStatistic
                .setText("Calories:      " + calories + "kcal");
    }
}