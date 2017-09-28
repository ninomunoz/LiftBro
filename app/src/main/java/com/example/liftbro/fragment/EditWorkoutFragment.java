package com.example.liftbro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.liftbro.Exercise;
import com.example.liftbro.R;
import com.example.liftbro.adapter.EditExerciseAdapter;
import com.example.liftbro.adapter.ExerciseAdapter;

import java.util.Arrays;
import java.util.List;

public class EditWorkoutFragment extends Fragment {

    private static final String ARG_TITLE_KEY = "title";

    private String mTitle;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public static EditWorkoutFragment newInstance(String title) {
        EditWorkoutFragment frag = new EditWorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTitle = getArguments().getString(ARG_TITLE_KEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_workout, container, false);
        updateToolbar();

        // Set up recycler view
        RecyclerView rvExercises = (RecyclerView)view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        EditExerciseAdapter adapter = new EditExerciseAdapter(getContext(), getDummyExercises());
        rvExercises.setAdapter(adapter);

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

    private List<Exercise> getDummyExercises() {
        return Arrays.asList(
                new Exercise("Bench Press", 3, 10, 150.0),
                new Exercise("Incline DB Flies", 4, 10, 50.0),
                new Exercise("Pushups", 3, 15, 0.0),
                new Exercise("Jog", 1800)
        );
    }
}
