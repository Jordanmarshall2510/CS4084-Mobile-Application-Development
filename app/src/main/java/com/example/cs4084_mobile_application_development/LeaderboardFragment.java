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
        return inflater.inflate(R.layout.fragment_leaderboard,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Database database = Database.getInstance();

        super.onViewCreated(view, savedInstanceState);
        View currentView = getView();

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

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Leaderboard.leaderboardValues = database.getLeaderboard();
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String [] values = Leaderboard.leaderboardValues;
        TextView[] textViews =
        {
            row1, row2, row3, row4, row5, row6, row7, row8, row9, row10
        };

        for(int i=0; i<10; i++){
            textViews[i].setText(values[i]);
            if (values[i].contains("ME")){
                textViews[i].setBackgroundColor(Color.parseColor("#00C6BE"));
                textViews[i].setTextColor(Color.parseColor("black"));
            }
        }

    }
}

class Leaderboard {
    public static String[] leaderboardValues;
}
