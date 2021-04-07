package com.example.cs4084_mobile_application_development;

public class stopStart {
    private static boolean isStarted = false;
    private static long startTime;
    private static long stopTime;

    public static boolean isStarted() {
        return isStarted;
    }

    public static void stop() {
        isStarted = false;
        stopTime = System.currentTimeMillis();
    }

    public static void start() {
        isStarted = true;
        startTime = System.currentTimeMillis();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static long getStopTime() {
        return stopTime;
    }
}
