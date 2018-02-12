package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * API Contract for the HabitTracket app.
 */
public final class HabitContract {

    public static class HabitEntry implements BaseColumns {
        // Constant for table name
        public static final String TABLE_NAME = "habits";

        // Constant for id column
        public static final String _ID = BaseColumns._ID;

        // Constant for  habit column column name
        public static final String HABIT_NAME = "habit";

        // Constant for habit streak column name
        public static final String HABIT_STREAK = "streak";
    }
}
