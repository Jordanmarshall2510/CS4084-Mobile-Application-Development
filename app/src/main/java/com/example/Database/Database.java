package com.example.Database;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database DBInstance;

    public static synchronized Database getInstance() {
        if(DBInstance == null) {
            DBInstance = new Database();
        }
        return DBInstance;
    }

    private static final String TAG = "Database";

    FirebaseFirestore database;
    String userID;
    DocumentReference totalDocument;
    DocumentReference dailysDocument;

    private long dailyDistance = 0;
    private long dailyCalories = 0;
    private long dailyTime = 0;
    private long totalDistance = 0;
    private long totalCalories = 0;
    private long totalTime = 0;

    private Database() {
        userID = "craig";
        database = FirebaseFirestore.getInstance();
        totalDocument = database.collection("userTotals").document(userID);
        dailysDocument = database.collection("userDailys").document(userID);

        initialiseDatabase();
    }

    private void initialiseDatabase() {
        dailysDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        dailyDistance = (long) document.get("dailyDistance");
                        dailyCalories = (long) document.get("dailyCalories");
                        dailyTime = (long) document.get("dailyTime");

                    } else {
                        database.collection("userDailys").document(userID).set(formatDocumentData('d'));
                    }
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            }
        });

        totalDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        totalDistance = (long) document.get("totalDistance");
                        totalCalories = (long) document.get("totalCalories");
                        totalTime = (long) document.get("totalTime");

                    } else {
                        database.collection("userTotals").document(userID).set(formatDocumentData('t'));
                    }
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private Map<String, Object> formatDocumentData(char type) {
        Map<String, Object> data = new HashMap<>();
        switch (type) {
            case 't':
                data.put("totalDistance", totalDistance);
                data.put("totalCalories", totalCalories);
                data.put("totalTime", totalTime);
                break;

            case 'd':
                data.put("dailyDistance", dailyDistance);
                data.put("dailyCalories", dailyCalories);
                data.put("dailyTime", dailyTime);
                break;
            default:
                Log.e(TAG, "formatDocumentData: Invalid type");
        }

        return data;
    }

    //Get from database
    public long getDailyDistance() {
        return dailyDistance;
    }

    public long getDailyCalories() {
        return dailyCalories;
    }

    public long getDailyTime() {
        return dailyTime;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public long getTotalCalories() {
        return totalCalories;
    }

    public long getTotalTime() {
        return totalTime;
    }

    //Add to database
    public void addToDailyDistance(long distance) {
        dailyDistance += distance;
        dailysDocument.update("dailyDistance", dailyDistance);
        addToTotalDistance(distance);
    }

    public void addToDailyCalories(long calories) {
        dailyCalories += calories;
        dailysDocument.update("dailyCalories", dailyCalories);
        addToTotalCalories(calories);
    }

    public void addToDailyTime(long time) {
        dailyTime += time;
        dailysDocument.update("dailyTime", dailyTime);
        addToTotalTime(time);
    }

    public void addToTotalDistance(long distance) {
        totalDistance += distance;
        totalDocument.update("totalDistance", totalDistance);
    }

    public void addToTotalCalories(long calories) {
        totalCalories += calories;
        totalDocument.update("totalCalories", totalCalories);
    }

    public void addToTotalTime(long time) {
        totalTime += time;
        totalDocument.update("totalTime", totalTime);
    }
}