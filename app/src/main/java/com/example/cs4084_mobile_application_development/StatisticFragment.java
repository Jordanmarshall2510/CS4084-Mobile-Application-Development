package com.example.cs4084_mobile_application_development;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;

import com.example.Database.Database;
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

        Database database = Database.getInstance();

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

        //Gets the time in minutes and hours for daily time
        double dailyTimeMinutes = (((double) database.getDailyTime() / 1000) / 60);
        double dailyTimeHours = ((((double) database.getDailyTime() / 1000) / 60) - dailyTimeMinutes) / 60;

        dailyTimeMinutes = Math.round(dailyTimeMinutes);

        //Gets the speed in meters per second for daily speed
        double dailyTime = (double) database.getDailyTime();
        double dailyDistance = (double) database.getDailyDistance();
        double dailySpeed = dailyDistance / dailyTime;
        double dailySpeedTotal = dailySpeed * 1000;

        // Change the daily text
        dailyDistanceTextStatistic
                .setText("Distance:    " + ((double) database.getDailyDistance() / 1000) + "km");
        dailyTimeTextStatistic
                .setText("Time:           " + dailyTimeHours +"h " + dailyTimeMinutes + "m");
        dailySpeedTextStatistic
                .setText("Speed:         " + ((double) Math.round(dailySpeedTotal * 100) / 100) + "m/s");
        dailyCaloriesBurntTextStatistic
                .setText("Calories:      " + database.getDailyCalories() + "kcal");

        //Gets the Time in minutes and hours for total time
        double totalTimeMinutes = (((double) database.getTotalTime() / 1000) / 60);
        double totalTimeHours = ((((double) database.getTotalTime() / 1000) / 60) - totalTimeMinutes) / 60;

        totalTimeMinutes = Math.round(totalTimeMinutes);

        //Gets the speed in meters per second for the total speed
        double totalTime = (double) database.getTotalTime();
        double totalDistance = (double) database.getTotalDistance();
        double totalSpeed = totalDistance / totalTime;
        double speedTotal = totalSpeed * 1000;

        //Change the total text
        totalDistanceTextStatistic
                .setText("Distance:    " + (((double) database.getTotalDistance())/ 1000) + "km");
        totalTimeTextStatistic
                .setText("Time:           " + totalTimeHours + "h " + totalTimeMinutes + "m");
        totalSpeedTextStatistic
                .setText("Speed:         " + ((double) Math.round(speedTotal * 100) / 100) + "m/s");
        totalCaloriesBurntTextStatistic
                .setText("Calories:      " + database.getTotalCalories() + "kcal");
    }
}