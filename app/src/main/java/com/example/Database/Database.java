package com.example.Database;

import android.util.Log;

import androidx.annotation.WorkerThread;

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
import java.util.concurrent.ExecutionException;

public class Database {
    private static Database DBInstance;

    /**
     * This function creates a singleton of the Cloud Firestore Database
     *
     * @return An instance of the database
     */
    public static synchronized Database getInstance() {
        if (DBInstance == null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DBInstance = new Database();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


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

    /**
     * This function initialises the references to the Cloud Firestore Database
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Database() throws ExecutionException, InterruptedException {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.w("AUTH", "Could not generate database handler, user is not authenticated");
        }
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ;
        database = FirebaseFirestore.getInstance();
        totalDocument = database.collection("userTotals").document(userID);
        dailysDocument = database.collection("userDailys").document(userID);
        collectionRef = database.collection("userTotals");

        initialiseDatabase();
    }

    /**
     * This function gets the collections and documents to the Cloud Firestore Database and adds them as local variables
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void initialiseDatabase() throws ExecutionException, InterruptedException {
        DocumentSnapshot document = Tasks.await(dailysDocument.get());
        if (document.exists()) {
            dailyDistance = (long) document.get("dailyDistance");
            dailyTime = (long) document.get("dailyTime");
        } else {
            database.collection("userDailys").document(userID).set(formatDocumentData('d'));
        }

        document = Tasks.await(totalDocument.get());
        if (document.exists()) {
            totalDistance = (long) document.get("totalDistance");
            totalTime = (long) document.get("totalTime");
        } else {
            database.collection("userTotals").document(userID).set(formatDocumentData('t'));
        }
    }

    /**
     * This function formats the local variables to be pushed to the Cloud Firestore Database
     *
     * @param type t for total and d for daily statistics
     * @return all data in a format compatible with the Cloud Firestore Database
     */
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

    /**
     * This function gets the total distance for the top 10 users from the database and puts them into a string array. It then formats the string to from highest to lowest distance.
     *
     * @return A formatted string array of distances from highest to lowest
     */
    //Get Leaderboard information
    @WorkerThread
    public String[] getLeaderboard() {

        String[] formattedLeaderboard = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

        try {
            List<DocumentSnapshot> result = Tasks.await(collectionRef.orderBy("totalDistance", Query.Direction.DESCENDING).limit(10).get()).getDocuments();
            int i = 0;
            for (DocumentSnapshot document : result) {
                formattedLeaderboard[i] = document.get("totalDistance") + " ";
                if (document.getId().equals(userID)) {
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

    /**
     * This function gets the users daily distance from the Cloud Firestore Database
     *
     * @return The users daily distance
     */
    //Get from database
    public long getDailyDistance() {
        return dailyDistance;
    }

    /**
     * This function gets the users daily time from the Cloud Firestore Database
     *
     * @return The users daily time
     */
    public long getDailyTime() {
        return dailyTime;
    }

    /**
     * This function gets the users total distance from the Cloud Firestore Database
     *
     * @return The users total distance
     */
    public long getTotalDistance() {
        return totalDistance;
    }

    /**
     * This function gets the users total time from the Cloud Firestore Database
     *
     * @return The users total time
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * This function adds the users daily distance to the Cloud Firestore Database
     *
     * @param distance
     */
    //Add to database
    public void addToDailyDistance(long distance) {
        dailyDistance += distance;
        dailysDocument.update("dailyDistance", dailyDistance);
        addToTotalDistance(distance);
    }

    /**
     * This function adds the users daily time to the Cloud Firestore Database
     *
     * @param time
     */
    public void addToDailyTime(long time) {
        dailyTime += time;
        dailysDocument.update("dailyTime", dailyTime);
        addToTotalTime(time);
    }

    /**
     * This function adds the users total distance to the Cloud Firestore Database
     *
     * @param distance
     */
    public void addToTotalDistance(long distance) {
        totalDistance += distance;
        totalDocument.update("totalDistance", totalDistance);
    }

    /**
     * This function adds the users total time to the Cloud Firestore Database
     *
     * @param time
     */
    public void addToTotalTime(long time) {
        totalTime += time;
        totalDocument.update("totalTime", totalTime);
    }
}