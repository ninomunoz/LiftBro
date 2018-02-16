package com.example.liftbro.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.liftbro.R;
import com.example.liftbro.adapter.LiftAdapter;
import com.example.liftbro.data.LiftContract;
import com.example.liftbro.model.Exercise;
import com.example.liftbro.model.WorkoutExercise;
import com.example.liftbro.util.Analytics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 2/15/18.
 */

public class LiftFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_STOPWATCH_TIME = "stopwatchTime";
    private static final String ARG_IS_STOPWATCH_RUNNING = "isStopwatchRunning";
    private static final String ARG_TIME_WHEN_STOPPED = "timeWhenStopped";
    private static final String ARG_TITLE_KEY = "title";
    private static final String ARG_WORKOUT_ID_KEY = "workoutId";

    @BindView(R.id.rv_exercises) RecyclerView rvExercises;
    @BindView(R.id.chronometer) Chronometer mChronometer;
    @BindView(R.id.tv_start) TextView tvStart;
    @BindView(R.id.tv_stop) TextView tvStop;

    private long mTimeWhenStopped = 0;
    private boolean mIsStopwatchRunning;
    private String mWorkoutName;
    private int mWorkoutId;
    private LiftAdapter mAdapter;

    public static LiftFragment newInstance(int workoutId, String title) {
        LiftFragment frag = new LiftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        args.putInt(ARG_WORKOUT_ID_KEY, workoutId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mWorkoutName = getArguments().getString(ARG_TITLE_KEY);
        mWorkoutId = getArguments().getInt(ARG_WORKOUT_ID_KEY);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lift, container, false);
        ButterKnife.bind(this, view);
        updateToolbar();

        // Set up recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(llm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new LiftAdapter(getContext());
        mAdapter.setHasStableIds(true);
        rvExercises.setAdapter(mAdapter);
        getLoaderManager().initLoader(3, null, this);

        // Set up stopwatch
        if (savedInstanceState != null) {
            mTimeWhenStopped = savedInstanceState.getLong(ARG_TIME_WHEN_STOPPED);
            mIsStopwatchRunning = savedInstanceState.getBoolean(ARG_IS_STOPWATCH_RUNNING);

            if (mIsStopwatchRunning) {
                startStopwatch(savedInstanceState.getLong(ARG_STOPWATCH_TIME));
            }
            else {
                mChronometer.setBase(SystemClock.elapsedRealtime() + mTimeWhenStopped);
            }
        }

        tvStart.setOnClickListener(this);
        tvStart.setTextColor(mIsStopwatchRunning ? Color.DKGRAY : Color.GREEN);
        tvStop.setTextColor(Color.RED);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO: Create menu with 'End Workout' option
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: handle 'End Workout'
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_STOPWATCH_TIME, mChronometer.getBase());
        outState.putBoolean(ARG_IS_STOPWATCH_RUNNING, mIsStopwatchRunning);
        outState.putLong(ARG_TIME_WHEN_STOPPED, mTimeWhenStopped);
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mWorkoutName);
    }

    private void startStopwatch(long base) {
        mChronometer.setBase(base);
        mChronometer.start();
        mIsStopwatchRunning = true;
        tvStart.setTextColor(Color.DKGRAY);
        tvStart.setOnClickListener(null);
        tvStop.setText(getString(R.string.stop));
        tvStop.setOnClickListener(this);
    }

    private void stopStopwatch() {
        tvStop.setText(getString(R.string.reset));
        tvStart.setTextColor(Color.GREEN);
        mTimeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
        mChronometer.stop();
        mIsStopwatchRunning = false;
    }

    private void resetStopwatch() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mTimeWhenStopped = 0;
        tvStop.setOnClickListener(null);
    }

    // View.OnClickListener implementation
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startStopwatch(SystemClock.elapsedRealtime() + mTimeWhenStopped);
                Analytics.logEventStartTimer(getActivity());
                break;
            case R.id.tv_stop:
                if (tvStop.getText().toString().equals(getString(R.string.stop))) { // stop
                    stopStopwatch();
                }
                else { // reset
                    resetStopwatch();
                }
                tvStart.setOnClickListener(this);
                break;
        }
    }

    // LoaderManager.LoaderCallbacks<Cursor> implementation
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = LiftContract.WorkoutExerciseEntry.COLUMN_WORKOUT_ID + " = ?";
        String[] selectionArgs = { Integer.toString(mWorkoutId) };
        return new CursorLoader(getActivity(),
                LiftContract.WorkoutExerciseEntry.CONTENT_URI,
                null, selection, selectionArgs, LiftContract.WorkoutExerciseEntry.COLUMN_POSITION);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        if (data.moveToFirst()) {
            do {
                final int workoutExerciseId = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry._ID));
                final int exerciseId = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_EXERCISE_ID));
                final int time = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_TIME));
                final int position = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_POSITION));
                int sets = 0, reps = 0;
                double weight = 0.0;
                Exercise exercise;

                if (time == 0) {
                    sets = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_SETS));
                    reps = data.getInt(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_REPS));
                    weight = data.getDouble(data.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_WEIGHT));
                }

                // Retrieve Exercise from database
                String[] projection = { LiftContract.ExerciseEntry.COLUMN_NAME };
                String selection = LiftContract.ExerciseEntry._ID + " = ?";
                String[] selectionArgs = { Integer.toString(exerciseId) };
                Cursor exerciseCursor = getContext().getContentResolver().query(
                        LiftContract.ExerciseEntry.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null
                );

                if (exerciseCursor.moveToFirst()) {
                    String exerciseName = exerciseCursor.getString(exerciseCursor.getColumnIndex(LiftContract.ExerciseEntry.COLUMN_NAME));
                    exercise = new Exercise(exerciseId, exerciseName, null);
                    if (time == 0) {
                        workoutExercises.add(new WorkoutExercise(workoutExerciseId, null, exercise, sets, reps, weight, position));
                    }
                    else {
                        workoutExercises.add(new WorkoutExercise(workoutExerciseId, null, exercise, time, position));
                    }
                }

            } while (data.moveToNext());
        }

        mAdapter.setWorkoutExercises(workoutExercises);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.setWorkoutExercises(null);
    }
}
