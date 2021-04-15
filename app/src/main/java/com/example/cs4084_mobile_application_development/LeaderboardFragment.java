package com.example.cs4084_mobile_application_development;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Database.Database;

public class LeaderboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Create instance of database class
        Database database = Database.getInstance();

        super.onViewCreated(view, savedInstanceState);
        View currentView = getView();

        //Find textview IDs and create textview objects
        TextView row1 = (TextView) currentView.findViewById(R.id.row1);
        TextView row2 = (TextView) currentView.findViewById(R.id.row2);
        TextView row3 = (TextView) currentView.findViewById(R.id.row3);
        TextView row4 = (TextView) currentView.findViewById(R.id.row4);
        TextView row5 = (TextView) currentView.findViewById(R.id.row5);
        TextView row6 = (TextView) currentView.findViewById(R.id.row6);
        TextView row7 = (TextView) currentView.findViewById(R.id.row7);
        TextView row8 = (TextView) currentView.findViewById(R.id.row8);
        TextView row9 = (TextView) currentView.findViewById(R.id.row9);
        TextView row10 = (TextView) currentView.findViewById(R.id.row10);

        //Using a thread, gets leaderboard results from database class. Data is already sorted.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Leaderboard.leaderboardValues = database.getLeaderboard();
            }
        });

        thread.start();

        //Join worker thread back into main thread
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] values = Leaderboard.leaderboardValues;
        TextView[] textViews =
                {
                        row1, row2, row3, row4, row5, row6, row7, row8, row9, row10
                };

        //Assign value to textview for leaderboard
        for (int i = 0; i < 10; i++) {
            if (values[i].contains("ME")) {
                String value = values[i].replace("ME", "");
                textViews[i].setText(((double) Integer.parseInt(value.trim()) / 1000) + " km");
                textViews[i].setBackgroundColor(Color.parseColor("#9500C6BE"));
                textViews[i].setTextColor(Color.parseColor("black"));
            } else {
                textViews[i].setText(((double) Integer.parseInt(values[i].trim()) / 1000) + " km");
            }
        }

    }
}

class Leaderboard {
    public static String[] leaderboardValues;
}
