package com.example.liftbro.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.example.liftbro.adapter.ExerciseAdapter;
import com.example.liftbro.R;
import com.example.liftbro.dialog.DeleteWorkoutDialogFragment;
import com.example.liftbro.dialog.RenameWorkoutDialogFragment;
import com.example.liftbro.util.Analytics;
import com.example.liftbro.util.FormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.liftbro.data.LiftContract.WorkoutExerciseEntry;
import static com.example.liftbro.data.LiftContract.ExerciseEntry;
import static com.example.liftbro.data.LiftContract.WorkoutEntry;

public class WorkoutDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        RenameWorkoutDialogFragment.RenameWorkoutListener, DeleteWorkoutDialogFragment.DeleteWorkoutListener, View.OnClickListener {

    private static final String ARG_TITLE_KEY = "title";
    private static final String ARG_WORKOUT_ID_KEY = "workoutId";
    private static final String ARG_STOPWATCH_TIME = "stopwatchTime";
    private static final String ARG_IS_STOPWATCH_RUNNING = "isStopwatchRunning";
    private static final String ARG_TIME_WHEN_STOPPED = "timeWhenStopped";

    @BindView(R.id.rv_exercises) RecyclerView rvExercises;
    @BindView(R.id.chronometer) Chronometer mChronometer;
    @BindView(R.id.tv_start) TextView tvStart;
    @BindView(R.id.tv_stop) TextView tvStop;

    private String mWorkoutName;
    private int mWorkoutId;
    private ExerciseAdapter mAdapter;

    private long mTimeWhenStopped = 0;
    private boolean mIsStopwatchRunning;

    public static WorkoutDetailFragment newInstance(int workoutId, String title) {
        WorkoutDetailFragment frag = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        args.putInt(ARG_WORKOUT_ID_KEY, workoutId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mWorkoutName = getArguments().getString(ARG_TITLE_KEY);
        mWorkoutId = getArguments().getInt(ARG_WORKOUT_ID_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        ButterKnife.bind(this, view);
        updateToolbar();

        // Set up recycler view
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new ExerciseAdapter(getContext());
        mAdapter.setHasStableIds(true);
        rvExercises.setAdapter(mAdapter);
        getLoaderManager().initLoader(1, null, this);

        tvStart.setOnClickListener(this);
        tvStart.setTextColor(Color.GREEN);
        tvStop.setTextColor(Color.RED);

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

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miEdit:
                editWorkout();
                return true;
            case R.id.miShare:
                shareWorkout();
                Analytics.logEventShareWorkout(getActivity(), mWorkoutId, mWorkoutName);
                return true;
            case R.id.miDelete:
                deleteWorkout();
                Analytics.logEventDeleteWorkout(getActivity(), mWorkoutId, mWorkoutName);
                return true;
            case R.id.miRename:
                renameWorkout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void editWorkout() {
        EditWorkoutFragment frag = EditWorkoutFragment.newInstance(mWorkoutId, mWorkoutName);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void renameWorkout() {
        RenameWorkoutDialogFragment dlg = RenameWorkoutDialogFragment.newInstance(mWorkoutName);
        dlg.setTargetFragment(WorkoutDetailFragment.this, 0);
        dlg.show(getActivity().getSupportFragmentManager(), RenameWorkoutDialogFragment.RENAME_WORKOUT_DIALOG_TAG);
    }

    private void deleteWorkout() {
        DeleteWorkoutDialogFragment dlg = new DeleteWorkoutDialogFragment();
        dlg.setTargetFragment(WorkoutDetailFragment.this, 0);
        dlg.show(getActivity().getSupportFragmentManager(), DeleteWorkoutDialogFragment.DELETE_WORKOUT_DIALOG_TAG);
    }

    private void shareWorkout() {
        String shareMsg = String.format(getString(R.string.share_msg), mWorkoutName);
        Cursor cursor = mAdapter.getCursor();

        if (cursor.moveToFirst()) {
            do {
                final int exerciseId = cursor.getInt(
                        cursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_EXERCISE_ID));

                String[] projection = { ExerciseEntry.COLUMN_NAME };
                String selection = ExerciseEntry._ID + " = ?";
                String[] selectionArgs = { Integer.toString(exerciseId) };

                Cursor exerciseCursor = getContext().getContentResolver().query(
                        ExerciseEntry.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null
                );
                exerciseCursor.moveToFirst();

                final String name = exerciseCursor.getString(
                        exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_NAME));
                final int sets = cursor.getInt(
                        cursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_SETS));
                final int reps = cursor.getInt(
                        cursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_REPS));
                final double weight = cursor.getDouble(
                        cursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_WEIGHT));
                final int time = cursor.getInt(
                        cursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_TIME));

                shareMsg += FormatUtil.formatSharedExercise(getContext(), name, sets, reps, weight, time) + "\n";
            }
            while (cursor.moveToNext());
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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

    // LoaderManager.LoaderCallbacks<Cursor> implementation
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String selection = WorkoutExerciseEntry.COLUMN_WORKOUT_ID + " = ?";
        String[] selectionArgs = { Integer.toString(mWorkoutId) };
        return new CursorLoader(getActivity(),
                WorkoutExerciseEntry.CONTENT_URI,
                null, selection, selectionArgs, WorkoutExerciseEntry.COLUMN_POSITION);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.setCursor(null);
    }

    // RenameWorkoutDialogFragment.RenameWorkoutListener implementation
    @Override
    public void onRename(String newWorkoutName) {
        mWorkoutName = newWorkoutName;
        updateToolbar();
        ContentValues values = new ContentValues();
        values.put(WorkoutEntry.COLUMN_NAME, newWorkoutName);
        getActivity().getContentResolver().update(
                ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, mWorkoutId),
                values, null, null);
        Analytics.logEventRenameWorkout(getActivity());
    }

    // DeleteWorkoutDialogFragment.DeleteWorkoutListener implementation
    @Override
    public void onDelete() {
        getActivity().getContentResolver().delete(
                ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, mWorkoutId),
                null, null);
        getActivity().onBackPressed();
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
}