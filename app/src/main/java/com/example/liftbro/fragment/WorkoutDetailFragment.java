package com.example.liftbro.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.LinearLayout;

import com.example.liftbro.Exercise;
import com.example.liftbro.adapter.ExerciseAdapter;
import com.example.liftbro.R;

import java.util.Arrays;
import java.util.List;

public class WorkoutDetailFragment extends Fragment {

    private static final String ARG_TITLE_KEY = "title";
    private String mTitle;
    private RecyclerView rvExercises;
    private ExerciseAdapter mAdapter;
    private FloatingActionButton mFab;
    private LinearLayout llTimer;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

    public static WorkoutDetailFragment newInstance(String title) {
        WorkoutDetailFragment frag = new WorkoutDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mTitle = getArguments().getString(ARG_TITLE_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        mFab = (FloatingActionButton)view.findViewById(R.id.fab_add_exercise);
        mFab.setVisibility(View.GONE);
        llTimer = (LinearLayout)view.findViewById(R.id.ll_timer);
        updateToolbar();

        // Set up recycler view
        rvExercises = (RecyclerView)view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new ExerciseAdapter(getContext(), getDummyExercises(), false);
        rvExercises.setAdapter(mAdapter);

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
                llTimer.setVisibility(View.GONE);
                mAdapter.setEditMode(true);
                rvExercises.setAdapter(mAdapter);
                mFab.setVisibility(View.VISIBLE);
                return true;
            case R.id.miShare:
                // TODO: Share workout
                return true;
            case R.id.miDelete:
                // TODO: Delete workout
                return true;
            case R.id.miRename:
                // TODO: Rename workout
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mTitle);
    }

    private List<Exercise> getDummyExercises() {
        return Arrays.asList(
                new Exercise("Bench Press", 3, 10, 150.0),
                new Exercise("Incline DB Flies", 4, 10, 50.0),
                new Exercise("Pushups", 3, 15, 0.0),
                new Exercise("Jog", 1800)
        );
    }
}