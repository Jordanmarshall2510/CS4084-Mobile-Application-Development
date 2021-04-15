package com.example.cs4084_mobile_application_development;

import androidx.databinding.ObservableArrayList;

import com.example.Database.Database;
import com.google.android.gms.maps.model.LatLng;

public class calculations {
    /**
     * Pushes the distance and time to the database for storage
     */
    public static void pushRouteToDatabase() {
        Database database = Database.getInstance();
        long distance = calculateTotalDistance(LocationService.getLocationPoints());
        long time = stopStart.getStopTime() - stopStart.getStartTime();
        database.addToDailyDistance(distance);
        database.addToDailyTime(time);
    }

    /**
     * This function finds the distance between two points
     *
     * @param one  The latitude and longitude of point one
     * @param two  The latitude and longitude of point two
     * @param unit Unit of measurement
     * @return The distance in kilometers from the map
     */
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

    /**
     * This function converts decimal degrees to radians
     *
     * @param deg The degrees
     * @return The change of degrees to radians
     */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees
     *
     * @param rad The radians
     * @return The change of radians to degrees
     */
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * Calculates total distance in meters
     *
     * @param points All the points in a route
     * @return The distance in meters
     */
    public static long calculateTotalDistance(ObservableArrayList<LatLng> points) {
        double result = 0;

        for (int i = 1; i < points.size(); i++) {
            result += distance(points.get(i), points.get(i - 1), 'K');
        }

        return (long) (result * 1000);
    }
}
