package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database helper for HabitTracker app. Manages database creation and version management.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    // Log tag
    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    // Constant for database name
    public static final String DATABASE_NAME = "habits";

    // Constant for database version
    public static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + "(" +
                HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HabitContract.HabitEntry.HABIT_NAME + " TEXT NOT NULL, " +
                HabitContract.HabitEntry.HABIT_LAST_LOGGED + " INTEGER DEFAULT 0, " +
                HabitContract.HabitEntry.HABIT_STREAK + " INTEGER DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase cb, int i, int i1) {

    }
}
