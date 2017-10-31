package com.example.liftbro.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.liftbro.R;
import com.example.liftbro.async.FilterExercisesTask;
import com.example.liftbro.dialog.AddExerciseDialogFragment;
import com.example.liftbro.util.Analytics;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.liftbro.data.LiftContract.ExerciseEntry;
import static com.example.liftbro.data.LiftContract.WorkoutExerciseEntry;
import static com.example.liftbro.data.LiftContract.MuscleGroupEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExerciseFragment extends Fragment implements AddExerciseDialogFragment.AddExerciseListener {

    private static final String ARG_WORKOUT_ID = "workoutId";

    @BindView(R.id.spinnerMuscleGroup) Spinner mSpinnerMuscleGroup;
    @BindView(R.id.spinnerExercise) Spinner mSpinnerExercise;
    @BindView(R.id.rbTime) RadioButton rbTime;

    private int mWorkoutId;

    public static AddExerciseFragment newInstance(int workoutId) {
        AddExerciseFragment frag = new AddExerciseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_WORKOUT_ID, workoutId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mWorkoutId = getArguments().getInt(ARG_WORKOUT_ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateToolbar();
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        ButterKnife.bind(this, view);

        loadMuscleGroups();

        // Hook up muscle group selection listener
        mSpinnerMuscleGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)mSpinnerMuscleGroup.getSelectedItem();
                long id = cursor.getLong(cursor.getColumnIndex(MuscleGroupEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(MuscleGroupEntry.COLUMN_NAME));
                mSpinnerMuscleGroup.setContentDescription(name);
                FilterExercisesTask filterTask = new FilterExercisesTask(AddExerciseFragment.this);
                filterTask.execute(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add_exercise, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miDone:
                addExercise();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.add_exercise));
    }

    private void loadMuscleGroups() {
        Cursor cursor = getActivity().getContentResolver().query(
                MuscleGroupEntry.CONTENT_URI,
                null, null, null,
                MuscleGroupEntry.COLUMN_NAME
        );

        SimpleCursorAdapter muscleGroupAdapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                cursor,
                new String[] { MuscleGroupEntry.COLUMN_NAME }, // fromColumn
                new int[] { android.R.id.text1 }, // toView
                0
        );

        muscleGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMuscleGroup.setAdapter(muscleGroupAdapter);
    }

    public void loadExercises(Cursor cursor) {
        SimpleCursorAdapter exerciseAdapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                cursor,
                new String[] { ExerciseEntry.COLUMN_NAME }, // fromColumn
                new int[] { android.R.id.text1 }, // toView
                0
        );

        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerExercise.setAdapter(exerciseAdapter);
        mSpinnerExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)mSpinnerExercise.getSelectedItem();
                String name = cursor.getString(cursor.getColumnIndex(ExerciseEntry.COLUMN_NAME));
                mSpinnerExercise.setContentDescription(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void addExercise() {
        Cursor cursor = (Cursor)mSpinnerExercise.getSelectedItem();
        String name = cursor.getString(cursor.getColumnIndex(ExerciseEntry.COLUMN_NAME));

        AddExerciseDialogFragment dlg = AddExerciseDialogFragment.newInstance(name, rbTime.isChecked());
        dlg.setTargetFragment(AddExerciseFragment.this, 0);
        dlg.show(getActivity().getSupportFragmentManager(), AddExerciseDialogFragment.ADD_EXERCISE_DIALOG_TAG);
    }

    @Override
    public void onFinishAdd(int sets, int reps, double weight) {
        Cursor cursor = (Cursor)mSpinnerExercise.getSelectedItem();
        long exerciseId = cursor.getLong(cursor.getColumnIndex(ExerciseEntry._ID));

        ContentValues values = new ContentValues();
        values.put(WorkoutExerciseEntry.COLUMN_WORKOUT_ID, mWorkoutId);
        values.put(WorkoutExerciseEntry.COLUMN_EXERCISE_ID, exerciseId);
        values.put(WorkoutExerciseEntry.COLUMN_SETS, sets);
        values.put(WorkoutExerciseEntry.COLUMN_REPS, reps);
        values.put(WorkoutExerciseEntry.COLUMN_WEIGHT, weight);
        values.put(WorkoutExerciseEntry.COLUMN_TIME, 0);

        getActivity().getContentResolver().insert(WorkoutExerciseEntry.CONTENT_URI, values);
        Analytics.logEventAddExercise(getActivity(), exerciseId);
        getActivity().onBackPressed();
    }

    @Override
    public void onFinishAdd(int time) {
        Cursor cursor = (Cursor)mSpinnerExercise.getSelectedItem();
        long exerciseId = cursor.getLong(cursor.getColumnIndex(ExerciseEntry._ID));

        ContentValues values = new ContentValues();
        values.put(WorkoutExerciseEntry.COLUMN_WORKOUT_ID, mWorkoutId);
        values.put(WorkoutExerciseEntry.COLUMN_EXERCISE_ID, exerciseId);
        values.put(WorkoutExerciseEntry.COLUMN_SETS, 0);
        values.put(WorkoutExerciseEntry.COLUMN_REPS, 0);
        values.put(WorkoutExerciseEntry.COLUMN_WEIGHT, 0);
        values.put(WorkoutExerciseEntry.COLUMN_TIME, time);

        getActivity().getContentResolver().insert(WorkoutExerciseEntry.CONTENT_URI, values);
        Analytics.logEventAddExercise(getActivity(), exerciseId);
        getActivity().onBackPressed();
    }
}
