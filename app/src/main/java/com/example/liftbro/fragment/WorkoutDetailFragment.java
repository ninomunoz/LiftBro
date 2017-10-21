package com.example.liftbro.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.liftbro.data.LiftContract;
import com.example.liftbro.model.DummyExercise;
import com.example.liftbro.adapter.ExerciseAdapter;
import com.example.liftbro.R;

import java.util.Arrays;
import java.util.List;

public class WorkoutDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, RenameWorkoutDialogFragment.RenameWorkoutListener {

    private static final String ARG_TITLE_KEY = "title";
    private static final String ARG_WORKOUT_ID_KEY = "workoutId";

    private String mTitle;
    private int mWorkoutId;
    private RecyclerView rvExercises;
    private ExerciseAdapter mAdapter;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

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
        mTitle = getArguments().getString(ARG_TITLE_KEY);
        mWorkoutId = getArguments().getInt(ARG_WORKOUT_ID_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        updateToolbar();

        // Set up recycler view
        rvExercises = view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new ExerciseAdapter(getContext());
        mAdapter.setHasStableIds(true);
        rvExercises.setAdapter(mAdapter);
        getLoaderManager().initLoader(1, null, this);

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
                EditWorkoutFragment frag = EditWorkoutFragment.newInstance(mTitle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            case R.id.miShare:
                // TODO: Share workout
                return true;
            case R.id.miDelete:
                // TODO: Delete workout
                return true;
            case R.id.miRename:
                renameWorkout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mTitle);
    }

    private void renameWorkout() {
        RenameWorkoutDialogFragment dlg = RenameWorkoutDialogFragment.newInstance(mTitle);
        dlg.setTargetFragment(WorkoutDetailFragment.this, 0);
        dlg.show(getActivity().getSupportFragmentManager(), AddWorkoutDialogFragment.ADD_WORKOUT_DIALOG_TAG);
    }

    // LoaderManager.LoaderCallbacks<Cursor> implementation
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
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
    public void onLoaderReset(Loader loader) {
        mAdapter.setCursor(null);
    }

    // RenameWorkoutDialogFragment.RenameWorkoutListener implementation
    @Override
    public void onRename(String newWorkoutName) {
        mTitle = newWorkoutName;
        updateToolbar();
        ContentValues values = new ContentValues();
        values.put(LiftContract.WorkoutEntry.COLUMN_NAME, newWorkoutName);
        getActivity().getContentResolver().update(
                ContentUris.withAppendedId(LiftContract.WorkoutEntry.CONTENT_URI, mWorkoutId),
                values, null, null);
    }
}