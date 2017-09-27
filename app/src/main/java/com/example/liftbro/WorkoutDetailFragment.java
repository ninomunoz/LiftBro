package com.example.liftbro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class WorkoutDetailFragment extends Fragment {

    private static final String ARG_TITLE_KEY = "title";
    private String mTitle;

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
        updateToolbar();

        // Set up recycler view
        RecyclerView rvExercises = (RecyclerView)view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        ExerciseAdapter adapter = new ExerciseAdapter(getContext(), getDummyExercises());
        rvExercises.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
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