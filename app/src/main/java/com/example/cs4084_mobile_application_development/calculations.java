package com.example.cs4084_mobile_application_development;

import androidx.databinding.ObservableArrayList;

import com.example.Database.Database;
import com.google.android.gms.maps.model.LatLng;

public class calculations {
    public static void pushRouteToDatabase() {
        Database database = Database.getInstance();
        long distance = calculateTotalDistance(LocationService.getLocationPoints());
        long calories = 0;
        long time = stopStart.getStopTime() - stopStart.getStartTime();
        System.out.println("Distance: " + distance);
        System.out.println("Time: " + time);
        database.addToDailyDistance(distance);
        database.addToDailyTime(time);
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
    public static long calculateTotalDistance(ObservableArrayList<LatLng> points)
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
