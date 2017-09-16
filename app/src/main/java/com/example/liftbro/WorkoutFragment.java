package com.example.liftbro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        RecyclerView rvWorkouts = (RecyclerView)view.findViewById(R.id.rv_workouts);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        rvWorkouts.setLayoutManager(glm);
        WorkoutAdapter adapter = new WorkoutAdapter(dummyWorkouts);
        rvWorkouts.setAdapter(adapter);
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

}
