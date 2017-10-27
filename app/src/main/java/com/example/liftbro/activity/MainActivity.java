package com.example.liftbro.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liftbro.R;
import com.example.liftbro.dialog.AboutDialogFragment;
import com.example.liftbro.fragment.WorkoutDetailFragment;
import com.example.liftbro.fragment.WorkoutFragment;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_WORKOUT_ID = "INTENT_EXTRA_WORKOUT_ID";
    public static final String INTENT_EXTRA_WORKOUT_NAME = "INTENT_EXTRA_WORKOUT_NAME";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private boolean mIsDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIsDualPane = findViewById(R.id.fragment_workout) != null;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            // Add workout fragment
            if (mIsDualPane) {
                WorkoutFragment workoutFragment = WorkoutFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_workout, workoutFragment).commit();
            }
            else {
                WorkoutFragment workoutFragment = WorkoutFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, workoutFragment).commit();
            }

            if (extras != null) {
                // Launched from widget - show workout details
                long id = extras.getLong(INTENT_EXTRA_WORKOUT_ID);
                String name = extras.getString(INTENT_EXTRA_WORKOUT_NAME);
                WorkoutDetailFragment newFragment = WorkoutDetailFragment.newInstance((int)id, name);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, newFragment).commit();
            }
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
            case R.id.miInvite:
                invite();
                return true;
            case R.id.miAbout:
                new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.ABOUT_DIALOG_TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void invite() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_msg));
            startActivity(Intent.createChooser(i, getString(R.string.chooser_msg)));
        } catch(Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }
}