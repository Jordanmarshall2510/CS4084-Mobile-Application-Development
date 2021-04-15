package com.example.cs4084_mobile_application_development;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Database.Database;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        database = Database.getInstance();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private Database database;
    private View currentView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        currentView = getView();

        //Updates the text on the home screen
        updateText();

        Button startStopButton = (Button) currentView.findViewById(R.id.startStopButton);
        if (stopStart.isStarted()) {
            startStopButton.setText("stop");
        } else {
            startStopButton.setText("start");
        }

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stopStart.isStarted()) {
                    stopStart.stop();
                    startStopButton.setText("start");
                    calculations.pushRouteToDatabase();
                    LocationService.resetLocationPoints();
                    ((MainActivity) getActivity()).updateLocation();
                    updateText();
                } else {
                    stopStart.start();
                    startStopButton.setText("stop");
                    ((MainActivity) getActivity()).updateLocation();
                }
            }
        });
    }

    /**
     * This function updates the statistics text that is to be displayed on the home screen
     */
    private void updateText() {
        // Get references to text views we want to change
        TextView distanceText = (TextView) currentView.findViewById(R.id.distanceText);
        TextView caloriesText = (TextView) currentView.findViewById(R.id.caloriesText);
        TextView timeText = (TextView) currentView.findViewById(R.id.timeText);

        //Gets the time in minutes and hours for daily time
        double dailyTimeHours = (((double) database.getDailyTime() / 1000) / 60) / 60;
        double dailyTimeMinutes = (((double) database.getDailyTime() / 1000) / 60) % 60;

        dailyTimeMinutes = Math.round(dailyTimeMinutes);

        //Gets the calories burned per day
        double dailyMinutesWalked = (((double) database.getDailyTime() / 1000) / 60);
        double dailyCaloriesBurned = Math.round(4.583 * dailyMinutesWalked);

        double dailyDistance = ((double) database.getDailyDistance() / 1000);

        DecimalFormat df = new DecimalFormat("#.#");

        // Change the text
        distanceText
                .setText(df.format(dailyDistance) + "km");
        caloriesText
                .setText((long) dailyCaloriesBurned + "kcal");
        timeText
                .setText((long) dailyTimeHours + "h " + (long) dailyTimeMinutes + "m");
    }

}
