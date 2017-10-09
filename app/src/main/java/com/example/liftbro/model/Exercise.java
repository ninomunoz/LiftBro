package com.example.liftbro.model;

/**
 * Created by i57198 on 10/9/17.
 */

public class Exercise {
    int mId;
    String mName;
    MuscleGroup mMuscleGroup;

    public Exercise(String name, MuscleGroup muscleGroup) {
        mName = name;
        mMuscleGroup = muscleGroup;
    }

    public Exercise(int id, String name, MuscleGroup muscleGroup) {
        mId = id;
        mName = name;
        mMuscleGroup = muscleGroup;
    }

    public void setId(int id) { mId = id; }
    public void setName(String name) { mName = name; }
    public void setMuscleGroup(MuscleGroup muscleGroup) { mMuscleGroup = muscleGroup; }

    public int getId() { return mId; }
    public String getName() { return mName; }
    public MuscleGroup getMuscleGroup() { return mMuscleGroup; }
}
