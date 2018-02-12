package com.example.android.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OverviewActivity extends AppCompatActivity {

    // TODO: ADD "LAST LOGGED DAY" TO DATABASE?
    // TODO: https://stackoverflow.com/questions/7363112/best-way-to-work-with-dates-in-android-sqlite

    // TODO: USE A CURSOR ADAPTER TO DISPLAY DATABASE QUERY IN A LISTVIEW?
    // TODO: https://github.com/codepath/android_guides/wiki/Populating-a-ListView-with-a-CursorAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
    }
}
