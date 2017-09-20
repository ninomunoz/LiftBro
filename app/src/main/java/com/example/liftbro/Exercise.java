package com.example.liftbro;

/**
 * Created by i57198 on 9/17/17.
 */

public class Exercise {
    String mName;
    int mSets = 0;
    int mReps = 0;
    double mWeight = 0.0;
    int mTime = 0; // in seconds

    public Exercise(String name, int sets, int reps, double weight) {
        mName = name;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public Exercise(String name, int sets, int reps, int time) {
        mName = name;
        mSets = sets;
        mReps = reps;
        mTime = time;
    }

    public Exercise(String name, int time) {
        mName = name;
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public int getSets() {
        return mSets;
    }

    public int getReps() {
        return mReps;
    }

    public double getWeight() {
        return mWeight;
    }

    public int getTime() {
        return mTime;
    }
}
