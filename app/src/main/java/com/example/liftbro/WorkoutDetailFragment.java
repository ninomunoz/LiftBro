package com.example.liftbro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class WorkoutDetailFragment extends Fragment {

    private List<Exercise> dummyExercises = Arrays.asList(
            new Exercise("Bench Press", 3, 10, 150.0),
            new Exercise("Incline DB Flies", 4, 10, 50.0),
            new Exercise("Pushups", 3, 15, 0.0),
            new Exercise("Jog", 1800)
    );

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

    public static WorkoutDetailFragment newInstance() {
        return new WorkoutDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);

        // Set up recycler view
        RecyclerView rvExercises = (RecyclerView)view.findViewById(R.id.rv_exercises);
        LinearLayoutManager glm = new LinearLayoutManager(getActivity());
        rvExercises.setLayoutManager(glm);
        rvExercises.addItemDecoration(new DividerItemDecoration(rvExercises.getContext(), DividerItemDecoration.VERTICAL));
        ExerciseAdapter adapter = new ExerciseAdapter(getContext(), dummyExercises);
        rvExercises.setAdapter(adapter);

        return view;
    }
}
