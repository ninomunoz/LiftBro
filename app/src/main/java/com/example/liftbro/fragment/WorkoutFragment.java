package com.example.liftbro.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liftbro.R;
import com.example.liftbro.adapter.WorkoutAdapter;
import com.example.liftbro.data.LiftContract;
import com.example.liftbro.dialog.AddWorkoutDialogFragment;

import static com.example.liftbro.data.LiftContract.WorkoutEntry;

public class WorkoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AddWorkoutDialogFragment.AddWorkoutListener {

    RecyclerView mRecyclerView;
    WorkoutAdapter mAdapter;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public static WorkoutFragment newInstance() {
        return new WorkoutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateToolbar();

        // Set up recycler view
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        mRecyclerView = view.findViewById(R.id.rv_workouts);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(glm);
        mAdapter = new WorkoutAdapter(getActivity());
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);

        // Hook up FAB
        FloatingActionButton fab = view.findViewById(R.id.fab_add_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWorkoutDialogFragment dlg = new AddWorkoutDialogFragment();
                dlg.setTargetFragment(WorkoutFragment.this, 0);
                dlg.show(getActivity().getSupportFragmentManager(), AddWorkoutDialogFragment.ADD_WORKOUT_DIALOG_TAG);
            }
        });
        return view;
    }

    private void updateToolbar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.app_name));
    }

    // LoaderManager.LoaderCallbacks<Cursor> implementation
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                WorkoutEntry.CONTENT_URI,
                null, null, null,
                WorkoutEntry.COLUMN_NAME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.setCursor(null);
    }

    // AddWorkoutDialogFragment.AddWorkoutListener implementation
    @Override
    public void onAdd(String workoutName) {
        ContentValues values = new ContentValues();
        values.put(LiftContract.WorkoutEntry.COLUMN_NAME, workoutName);
        getActivity().getContentResolver().insert(WorkoutEntry.CONTENT_URI, values);
    }
}