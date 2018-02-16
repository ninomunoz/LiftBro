package com.example.liftbro.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.data.LiftContract;
import com.example.liftbro.dialog.EditExerciseDialogFragment;
import com.example.liftbro.R;
import com.example.liftbro.adapter.EditExerciseAdapter;
import com.example.liftbro.helper.ExerciseTouchHelper;
import com.example.liftbro.model.Exercise;
import com.example.liftbro.model.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditWorkoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
        EditExerciseDialogFragment.EditExerciseListener, ExerciseTouchHelper.OnStartDragListener {

    private static final String ARG_TITLE_KEY = "title";
    private static final String ARG_WORKOUT_ID_KEY = "workoutId";

    @BindView(R.id.rv_exercises) RecyclerView rvExercises;
    @BindView(R.id.fab_add_exercise) FloatingActionButton mFab;
    @BindView(R.id.tv_add_exercise) TextView tvAddExercise;

    private String mTitle;
    private int mWorkoutId;
    private EditExerciseAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public static EditWorkoutFragment newInstance(int workoutId, String title) {
        EditWorkoutFragment frag = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_WORKOUT_ID_KEY, workoutId);
        args.putString(ARG_TITLE_KEY, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTitle = getArguments().getString(ARG_TITLE_KEY);
        mWorkoutId = getArguments().getInt(ARG_WORKOUT_ID_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);
        ButterKnife.bind(this, view);
        updateToolbar();

        // Set up recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(llm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new EditExerciseAdapter(getContext(), this);
        mAdapter.setHasStableIds(true);
        mAdapter.setOnClickListener(this);
        rvExercises.setAdapter(mAdapter);
        getLoaderManager().initLoader(2, null, this);

        // Add touch helper
        ExerciseTouchHelper callback = new ExerciseTouchHelper(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvExercises);

        // Hook up FAB
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExerciseFragment frag = AddExerciseFragment.newInstance(mWorkoutId);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, frag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        // Save positions to database
        List<WorkoutExercise> exercises = mAdapter.getWorkoutExercises();
        for (int i = 0; i < exercises.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(LiftContract.WorkoutExerciseEntry.COLUMN_POSITION, i);
            getActivity().getContentResolver().update(
                    ContentUris.withAppendedId(LiftContract.WorkoutExerciseEntry.CONTENT_URI, exercises.get(i).getId()),
                    values, null, null
            );
        }

        super.onPause();
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mTitle);
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
        tvAddExercise.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.setWorkoutExercises(null);
    }

    // View.OnClickListener implementation
    @Override
    public void onClick(View view) {
        int position = rvExercises.indexOfChild(view);
        long id = mAdapter.getItemId(position);
        final String name = ((TextView)view.findViewById(R.id.tv_exercise_name)).getText().toString();

        List<WorkoutExercise> workoutExercises = mAdapter.getWorkoutExercises();
        WorkoutExercise workoutExercise = workoutExercises.get(position);

        final int sets = workoutExercise.getSets();
        final int reps = workoutExercise.getReps();
        final double weight = workoutExercise.getWeight();
        final int time = workoutExercise.getTime();

        EditExerciseDialogFragment dlg = EditExerciseDialogFragment.newInstance(id, name, sets, reps, weight, time);
        dlg.setTargetFragment(EditWorkoutFragment.this, 0);
        dlg.show(getActivity().getSupportFragmentManager(), EditExerciseDialogFragment.EDIT_EXERCISE_DIALOG_TAG);
    }

    // EditExerciseDialogFragment.EditExerciseListener implementation
    @Override
    public void onFinishEdit(long id, int sets, int reps, double weight, int time) {
        ContentValues values = new ContentValues();
        values.put(LiftContract.WorkoutExerciseEntry.COLUMN_SETS, sets);
        values.put(LiftContract.WorkoutExerciseEntry.COLUMN_REPS, reps);
        values.put(LiftContract.WorkoutExerciseEntry.COLUMN_WEIGHT, weight);
        values.put(LiftContract.WorkoutExerciseEntry.COLUMN_TIME, time);
        getActivity().getContentResolver().update(
                ContentUris.withAppendedId(LiftContract.WorkoutExerciseEntry.CONTENT_URI, id),
                values, null, null);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}