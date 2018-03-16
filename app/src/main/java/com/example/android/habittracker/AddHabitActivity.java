package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDbHelper;

public class AddHabitActivity extends AppCompatActivity {

    // Log tag
    private static final String LOG_TAG = AddHabitActivity.class.getSimpleName();

    // Reference to instance of database helper class
    HabitDbHelper mHabitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Store global reference to a new database helper object
        mHabitDbHelper = new HabitDbHelper(this);

        // FOR DEMONSTRATION: call addHabit method with sample data
        addHabit();

        // FOR DEMONSTRATION: call queryDatabase and display sample data
        queryDatabase();
    }

    /**
     * Method to add a (dummy, for now) habit to the database
     */
    private void addHabit() {

        // Get a writable database object
        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        // Create a ContentValues object
        ContentValues values = new ContentValues();
        // Add data to the ContentValues object
        values.put(HabitContract.HabitEntry.HABIT_NAME, getString(R.string.testing_habit_name));
        values.put(HabitContract.HabitEntry.HABIT_LAST_LOGGED, 0);
        values.put(HabitContract.HabitEntry.HABIT_STREAK, 0);

        // Add habit to database by inserting the ContentValues object.
        // Store return value. Value will be either row number or "-1" if operation was unsuccessful.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        if (newRowId != -1) {
            // Show toast for successful insertion
            Toast.makeText(this, R.string.message_add_habit_succeeded, Toast.LENGTH_SHORT).show();
        } else {
            // Show toast for failed insertion
            Toast.makeText(this, R.string.message_add_habit_failed, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method to query the database for current habits and display returned data
     */
    private void queryDatabase() {

        // Get a readable database object
        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();

        // Create projection to define the table columns we want to get data from
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.HABIT_NAME,
                HabitContract.HabitEntry.HABIT_LAST_LOGGED,
                HabitContract.HabitEntry.HABIT_STREAK
        };

        // Query the database and return data in a Cursor object
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        // Get reference to textview in layout that will be used to show database info and the query results
        TextView displayView = findViewById(R.id.query_results);

        try {
            // Create a header in the Text View that looks like this:
            //
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText(getString(R.string.testing_number_of_entries_start) + cursor.getCount() +
                    getString(R.string.testing_number_of_entries_end) + "\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.HABIT_NAME + " - " +
                    HabitContract.HabitEntry.HABIT_LAST_LOGGED + " - " +
                    HabitContract.HabitEntry.HABIT_STREAK + " - " + "\n\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int habitNameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_NAME);
            int lastLoggedColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_LAST_LOGGED);
            int streakLengthColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.HABIT_STREAK);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentHabitName = cursor.getString(habitNameColumnIndex);
                int currentLastLogged = cursor.getInt(lastLoggedColumnIndex);
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
