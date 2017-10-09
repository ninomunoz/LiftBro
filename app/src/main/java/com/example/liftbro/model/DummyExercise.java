package com.example.liftbro.model;

/**
 * Created by i57198 on 9/17/17.
 */

public class DummyExercise {
    String mName;
    int mSets = 0;
    int mReps = 0;
    double mWeight = 0.0;
    int mTime = 0; // in seconds

    public DummyExercise(String name, int sets, int reps, double weight) {
        mName = name;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public DummyExercise(String name, int time) {
        mName = name;
        mTime = time;
    }
}