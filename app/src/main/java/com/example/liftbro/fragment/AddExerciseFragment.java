package com.example.liftbro.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.liftbro.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddExerciseFragment extends Fragment {


    public AddExerciseFragment() {
        // Required empty public constructor
    }

    public static AddExerciseFragment newInstance() { return new AddExerciseFragment(); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        updateToolbar();
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);

        // Set up muscle group spinner
        Spinner spinnerMuscleGroup = (Spinner)view.findViewById(R.id.spinnerMuscleGroup);
        ArrayAdapter<String> muscleGroupAdapter = new ArrayAdapter<String>(getActivity(),
                                                    android.R.layout.simple_spinner_dropdown_item,
                                                    getResources().getStringArray(R.array.muscle_array));
        muscleGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMuscleGroup.setAdapter(muscleGroupAdapter);

        // Set up exercise spinner
        Spinner spinnerExercise = (Spinner)view.findViewById(R.id.spinnerExercise);
        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<String>(getActivity(),
                                                    android.R.layout.simple_spinner_dropdown_item,
                                                    getDummyExercises());
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExercise.setAdapter(exerciseAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add_exercise, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miDone:
                // TODO: Add exercise to workout
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Exercise");
    }

    private List<String> getDummyExercises() {
        return Arrays.asList(
                "Barbell Reverse Curl",
                "Barbell Reverse Preacher Curl",
                "Cable Reverse Grip Curl",
                "Cable Reverse Preacher Curl",
                "Cable Wrist Curl",
                "Dumbbell Reverse Preacher Curl",
                "Dumbbell Reverse Wrist Curl",
                "EZ Bar Reverse Grip Preacher Curl",
                "Modified Pushup to Forearms",
                "Smith Machine Seated Wrist Curl",
                "Wrist Circles"
        );
    }

}
