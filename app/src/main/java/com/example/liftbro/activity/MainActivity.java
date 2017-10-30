package com.example.liftbro.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.appinvite.AppInviteInvitation;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_WORKOUT_ID = "INTENT_EXTRA_WORKOUT_ID";
    public static final String INTENT_EXTRA_WORKOUT_NAME = "INTENT_EXTRA_WORKOUT_NAME";
    private static final int REQUEST_INVITE = 1;

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
            long workoutId = 0;
            if (extras != null) {
                workoutId = extras.getLong(INTENT_EXTRA_WORKOUT_ID);
            }

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

            if (extras != null && workoutId != 0) {
                // Launched from widget - show workout details
                String name = extras.getString(INTENT_EXTRA_WORKOUT_NAME);
                WorkoutDetailFragment newFragment = WorkoutDetailFragment.newInstance((int)workoutId, name);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, newFragment).commit();
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
                            getSupportActionBar().setTitle(getString(R.string.app_name));
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
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.app_name))
                .setMessage(getString(R.string.invite_msg))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }
}