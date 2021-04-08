package com.example.Database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;
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
    CollectionReference collectionRef;

    private long dailyDistance = 0;
    private long dailyTime = 0;
    private long totalDistance = 0;
    private long totalTime = 0;

    private Database() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.w("AUTH", "Could not generate database handler, user is not authenticated");
        }
        userID =  FirebaseAuth.getInstance().getCurrentUser().getUid();;
        database = FirebaseFirestore.getInstance();
        totalDocument = database.collection("userTotals").document(userID);
        dailysDocument = database.collection("userDailys").document(userID);
        collectionRef = database.collection("userTotals");

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
                data.put("totalTime", totalTime);
                break;

            case 'd':
                data.put("dailyDistance", dailyDistance);
                data.put("dailyTime", dailyTime);
                break;
            default:
                Log.e(TAG, "formatDocumentData: Invalid type");
        }

        return data;
    }

    //Get Leaderboard information
    @WorkerThread
    public String[] getLeaderboard() {

        String[] formattedLeaderboard = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

        try {
            List<DocumentSnapshot> result = Tasks.await(collectionRef.orderBy("totalDistance", Query.Direction.DESCENDING).limit(10).get()).getDocuments();
            int i = 0;
            for (DocumentSnapshot document : result) {
                formattedLeaderboard[i] = document.get("totalDistance") + " ";
                if(document.getId().equals(userID)) {
                    formattedLeaderboard[i] += "\t ME";
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return formattedLeaderboard;
        }
    }

    //Get from database
    public long getDailyDistance() {
        return dailyDistance;
    }

    public long getDailyTime() {
        return dailyTime;
    }

    public long getTotalDistance() {
        return totalDistance;
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

    public void addToDailyTime(long time) {
        dailyTime += time;
        dailysDocument.update("dailyTime", dailyTime);
        addToTotalTime(time);
    }

    public void addToTotalDistance(long distance) {
        totalDistance += distance;
        totalDocument.update("totalDistance", totalDistance);
    }

    public void addToTotalTime(long time) {
        totalTime += time;
        totalDocument.update("totalTime", totalTime);
    }
}