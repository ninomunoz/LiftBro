package com.example.liftbro;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private List<String> dummyWorkouts = Arrays.asList(
            "Chest", "Tuesday", "Abs", "Legs", "Full Body");

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
        RecyclerView rvWorkouts = (RecyclerView)view.findViewById(R.id.rv_workouts);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        rvWorkouts.setLayoutManager(glm);
        WorkoutAdapter adapter = new WorkoutAdapter(getActivity(), dummyWorkouts);
        rvWorkouts.setAdapter(adapter);

        // Hook up FAB
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Launch Add Workout dialog
                Toast.makeText(getContext(), "Clicked Add Workout FAB", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void updateToolbar() {
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle(getString(R.string.app_name));
    }
}
