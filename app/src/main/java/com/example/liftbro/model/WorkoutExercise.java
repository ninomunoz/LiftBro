package com.example.liftbro.model;

/**
 * Created by i57198 on 10/9/17.
 */

public class WorkoutExercise {
    int mId;
    Workout mWorkout;
    Exercise mExercise;
    int mSets;
    int mReps;
    double mWeight;
    int mTime;
    int mPosition;

    public WorkoutExercise(Workout workout, Exercise exercise, int sets, int reps, double weight, int position) {
        mWorkout = workout;
        mExercise = exercise;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
        mPosition = position;
    }

    public WorkoutExercise(int id, Workout workout, Exercise exercise, int sets, int reps, double weight, int position) {
        mId = id;
        mWorkout = workout;
        mExercise = exercise;
        mSets = sets;
        mReps = reps;
        mWeight = weight;
        mPosition = position;
    }

    public WorkoutExercise(Workout workout, Exercise exercise, int time, int position) {
        mWorkout = workout;
        mExercise = exercise;
        mTime = time;
        mPosition = position;
    }

    public WorkoutExercise(int id, Workout workout, Exercise exercise, int time, int position) {
        mId = id;
        mWorkout = workout;
        mExercise = exercise;
        mTime = time;
        mPosition = position;
    }

    public void setId(int id) { mId = id; }
    public void setWorkout(Workout workout) { mWorkout = workout; }
    public void setExercise(Exercise exercise) { mExercise = exercise; }
    public void setSets(int sets) { mSets = sets; }
    public void setReps(int reps) { mReps = reps; }
    public void setWeight(double weight) { mWeight = weight; }
    public void setTime(int time) { mTime = time; }
    public void setPosition(int position) { mPosition = position; }

    public int getId() { return mId; }
    public Workout getWorkout() { return mWorkout; }
    public Exercise getExercise() { return mExercise; }
    public int getSets() { return mSets; }
    public int getReps() { return mReps; }
    public double getWeight() { return mWeight; }
    public int getTime() { return mTime; }
    public int getPosition() { return mPosition; }
}