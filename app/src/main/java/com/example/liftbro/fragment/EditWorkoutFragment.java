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

public class EditWorkoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, EditExerciseDialogFragment.EditExerciseListener {

    private static final String ARG_TITLE_KEY = "title";
    private static final String ARG_WORKOUT_ID_KEY = "workoutId";

    private String mTitle;
    private int mWorkoutId;
    EditExerciseAdapter mAdapter;
    private RecyclerView rvExercises;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

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
        updateToolbar();

        // Set up recycler view
        rvExercises = (RecyclerView)view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new EditExerciseAdapter(getContext());
        mAdapter.setHasStableIds(true);
        mAdapter.setOnClickListener(this);
        rvExercises.setAdapter(mAdapter);
        getLoaderManager().initLoader(2, null, this);

        // Hook up FAB
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_exercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExerciseFragment frag = AddExerciseFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag);
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
                null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.setCursor(null);
    }

    // View.OnClickListener implementation
    @Override
    public void onClick(View view) {
        int position = rvExercises.indexOfChild(view);
        long id = mAdapter.getItemId(position);
        final String name = ((TextView)view.findViewById(R.id.tv_exercise_name)).getText().toString();

        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);

        final int sets = cursor.getInt(
                cursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_SETS));
        final int reps = cursor.getInt(
                cursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_REPS));
        final double weight = cursor.getDouble(
                cursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_WEIGHT));
        final int time = cursor.getInt(
                cursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_TIME));

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
}
