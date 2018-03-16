package com.example.android.habittracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Create an Intent to launch new AddHabitActivity
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);

    }
}
