package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDbHelper;

public class AddHabitActivity extends AppCompatActivity {

    // TODO: INCLUDE CODE TO CONVERT TO/FROM SIMPLEDATEFORMAT AND MILLISECONDS TO BE STORED IN DATABASE
    // TODO: https://stackoverflow.com/questions/19419374/android-convert-date-and-time-to-milliseconds

    // Log tag
    private static final String LOG_TAG = AddHabitActivity.class.getSimpleName();

    // Reference to instance of database helper class
    HabitDbHelper mHabitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // TODO: INITIALIZE A GLOBAL INSTANCE OF THE DATABASE HELPER CLASS. RECONFIGURE ONCE INSERT & QUERY METHODS WORKING...
        mHabitDbHelper = new HabitDbHelper(this);

        // TODO: FOR TESTING PURPOSES - RELOCATE ONCE I'VE CONFIRMED THAT THE DATABASE IS BEING CREATED PROPERLY
        // Call the addHabit method
        addHabit();
    }

    private void addHabit() {
//        // Get instance of our database helper class
//        HabitDbHelper habitDbHelper = new HabitDbHelper(this);

        Log.v(LOG_TAG,"Entering addHabit method.");

        // Get a writable database object
        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        // Create a ContentValues object
        ContentValues values = new ContentValues();
        // Add data to the ContentValues object
        values.put(HabitContract.HabitEntry.HABIT_NAME,"Meditate");
        values.put(HabitContract.HabitEntry.HABIT_LAST_LOGGED,0);
        values.put(HabitContract.HabitEntry.HABIT_STREAK,0);

        // Add habit to database by inserting the ContentValues object.
        // Store return value. Value will be either row number or "-1" if operation was unsuccessful.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME,null, values);

        if (newRowId != -1) {
            Log.v(LOG_TAG,"Successfully inserted habit into database");
            // Call the queryDatabase method
            queryDatabase();
        } else {
            Log.v(LOG_TAG,"Failed to insert habit into database");
        }

    }

    private void queryDatabase() {
        Log.v(LOG_TAG,"Entering queryDatabase method.");

        // Get a readable database object
        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.HABIT_NAME,
                HabitContract.HabitEntry.HABIT_LAST_LOGGED,
                HabitContract.HabitEntry.HABIT_STREAK
        };

        String selection = HabitContract.HabitEntry._ID + "=?";
        String[] selectionArgs = {"*"};

//        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,null,null,null,null,null,null);

        TextView displayView = findViewById(R.id.query_results);

        try {
            // Create a header in the Text View that looks like this:
            //
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The habits table contains " + cursor.getCount() + " entries:\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.HABIT_NAME + " - " +
                    HabitContract.HabitEntry.HABIT_LAST_LOGGED + " - " +
                    HabitContract.HabitEntry.HABIT_STREAK + " - " + "\n\n");

            Log.v(LOG_TAG,"In queryDatabase method; storing Cursor's column indices.");
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int habitNameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_NAME);
            int lastLoggedColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_LAST_LOGGED);
            Log.v(LOG_TAG,"Value of lastLoggedColumnIndex is: " + Integer.toString(lastLoggedColumnIndex));
            int streakLengthColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_STREAK);

            Log.v(LOG_TAG,"In queryDatabase method; retrieving values from Cursor object.");
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                Log.v(LOG_TAG,"Reading id column value...");
                int currentID = cursor.getInt(idColumnIndex);
                Log.v(LOG_TAG,"Reading habit name column value...");
                String currentHabitName = cursor.getString(habitNameColumnIndex);
                Log.v(LOG_TAG,"Reading last logged date column value...");
                int currentLastLogged = cursor.getInt(lastLoggedColumnIndex);
                Log.v(LOG_TAG,"Reading streak length column value...");
                int currentStreakLength = cursor.getInt(streakLengthColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append("\n" + currentID + " - " +
                        currentHabitName + " - " +
                        currentLastLogged + " - " +
                        currentStreakLength);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }



}
