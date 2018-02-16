package com.example.liftbro.model;

/**
 * Created by i57198 on 2/16/18.
 */

public class CompletedSet {
    int mId;
    WorkoutExercise mWorkoutExercise;
    int mReps;
    double mWeight;
    int mTime;
    int mDate;

    public CompletedSet(WorkoutExercise workoutExercise, int reps, double weight, int date) {
        mWorkoutExercise = workoutExercise;
        mReps = reps;
        mWeight = weight;
        mDate = date;
    }

    public CompletedSet(int id, WorkoutExercise workoutExercise, int reps, double weight, int date) {
        mId = id;
        mWorkoutExercise = workoutExercise;
        mReps = reps;
        mWeight = weight;
        mDate = date;
    }

    public CompletedSet(WorkoutExercise workoutExercise, int time, int date) {
        mWorkoutExercise = workoutExercise;
        mTime = time;
        mDate = date;
    }

    public CompletedSet(int id, WorkoutExercise workoutExercise, int time, int date) {
        mId = id;
        mWorkoutExercise = workoutExercise;
        mTime = time;
        mDate = date;
    }

    public void setId(int id) { mId = id; }
    public void setWorkoutExercise(WorkoutExercise workoutExercise) { mWorkoutExercise = workoutExercise; }
    public void setReps(int reps) { mReps = reps; }
    public void setWeight(double weight) { mWeight = weight; }
    public void setTime(int time) { mTime = time; }
    public void setDate(int date) { mDate = date; }

    public int getId() { return mId; }
    public WorkoutExercise getWorkoutExercise() { return mWorkoutExercise; }
    public int getReps() { return mReps; }
    public double getWeight() { return mWeight; }
    public int getTime() { return mTime; }
    public int getDate() { return mDate; }
}
