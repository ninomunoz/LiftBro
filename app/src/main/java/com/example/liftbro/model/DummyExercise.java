package com.example.liftbro.model;

/**
 * Created by i57198 on 9/17/17.
 */

public class DummyExercise {
    int mId;
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

    public DummyExercise(int id, String name, int sets, int reps, double weight) {
        mId = id;
        mName = name;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
    }

    public DummyExercise(String name, int time) {
        mName = name;
        mTime = time;
    }

    public DummyExercise(int id, String name, int time) {
        mId = id;
        mName = name;
        mTime = time;
    }

    public void setId(int id) { mId = id; }
    public void setName(String name) { mName = name; }
    public void setSets(int sets) { mSets = sets; }
    public void setReps(int reps) { mReps = reps; }
    public void setWeight(double weight) { mWeight = weight; }
    public void setTime(int time) { mTime = time; }

    public int getId() { return mId; }
    public String getName() {
        return mName;
    }
    public int getSets() {
        return mSets;
    }
    public int getReps() {
        return mReps;
    }
    public double getWeight() { return mWeight; }
    public int getTime() {
        return mTime;
    }
}
