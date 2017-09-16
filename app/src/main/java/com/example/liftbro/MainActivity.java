package com.example.liftbro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            // If restoring, return to prevent overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            // Add workout fragment to fragment container
            WorkoutFragment workoutFragment = WorkoutFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, workoutFragment).commit();
        }
    }
}
