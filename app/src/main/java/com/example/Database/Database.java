package com.example.Database;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private static Database DBInstance;

    public static synchronized Database getInstance() {
        if(DBInstance == null) {
            DBInstance = new Database();
        }
        return DBInstance;
    }

    FirebaseFirestore database;
    String userID;
    DocumentReference userDocument;

    private long dailyDistance;
    private long dailyCalories;
    private long dailyTime;
    private long totalDistance;
    private long totalCalories;
    private long totalTime;

    private Database() {
        userID = "rioghan";
        database = FirebaseFirestore.getInstance();
        userDocument = database.collection("users").document(userID);
    }

    public long getDailyDistance() {
        dailyDistance = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    dailyDistance = (long) document.get("dailyDistance");
                }
            }
        });
        return dailyDistance;
    }

    public long getDailyCalories() {
        dailyCalories = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    dailyCalories = (long) document.get("dailyCalories");
                }
            }
        });
        return dailyCalories;
    }

    public long getDailyTime() {
        dailyTime = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    dailyTime = (long) document.get("dailyTime");
                }
            }
        });
        return dailyTime;
    }

    public long getTotalDistance() {
        totalDistance = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    totalDistance = (long) document.get("totalDistance");
                }
            }
        });
        return totalDistance;
    }

    public long getTotalCalories() {
        totalCalories = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    totalCalories = (long) document.get("totalCalories");
                }
            }
        });
        return totalCalories;
    }

    public long getTotalTime() {
        totalTime = 0;
        userDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    totalTime = (long) document.get("totalTime");
                }
            }
        });
        return totalTime;
    }

    public void addToDailyDistance(long distance) {
        userDocument.update("dailyDistance", getDailyDistance() + distance);
    }

    public void addToDailyCalories(long calories) {
        userDocument.update("dailyCalories", getDailyCalories() + calories);
    }

    public void addToDailyTime(long time) {
        userDocument.update("dailyTime", getDailyTime() + time);
    }

    public void addToTotalDistance(long distance) {
        userDocument.update("totalDistance", getTotalDistance() + distance);
    }

    public void addToTotalCalories(long calories) {
        userDocument.update("totalCalories", getTotalCalories() + calories);
    }

    public void addToTotalTime(long time) {
        userDocument.update("totalTime", getTotalTime() + time);
    }
}
