package com.example.cs4084_mobile_application_development;

public class stopStart {
    private static boolean isStarted = false;

    public static boolean isStarted() {
        return isStarted;
    }

    public static void stop() {
        isStarted = false;
    }

    public static void start() {
        isStarted = true;
    }
}
