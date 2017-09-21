package com.example.liftbro;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {

            // If restoring, return to prevent overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            // Add workout fragment to fragment container
            WorkoutFragment workoutFragment = WorkoutFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, workoutFragment).commit();

            getSupportFragmentManager().addOnBackStackChangedListener(
                    new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                getSupportActionBar().setHomeButtonEnabled(true);
                            }
                            else {
                                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                            }
                        }
                    }
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
