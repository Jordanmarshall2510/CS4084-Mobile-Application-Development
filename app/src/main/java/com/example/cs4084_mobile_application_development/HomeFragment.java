package com.example.cs4084_mobile_application_development;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        View currentView = getView();

        // Get references to text views we want to change
        TextView distanceText = (TextView) currentView.findViewById(R.id.distanceText);
        TextView caloriesText = (TextView) currentView.findViewById(R.id.caloriesText);
        TextView timeText = (TextView) currentView.findViewById(R.id.timeText);

        // Get references to image views we want to change
        ImageView distanceImage = (ImageView) currentView.findViewById(R.id.distanceImage);
        ImageView caloriesImage = (ImageView) currentView.findViewById(R.id.caloriesImage);
        ImageView timeImage = (ImageView) currentView.findViewById(R.id.timeImage);

        // Change the text
        distanceText.setText("1.05");
        caloriesText.setText("150");
        timeText.setText("0h 25m");

        // Change the image colours
        distanceImage.setColorFilter(Color.parseColor("#ff3030"));
        caloriesImage.setColorFilter(Color.parseColor("#fba00e"));
        timeImage.setColorFilter(Color.parseColor("#0099ff"));

    }

}
