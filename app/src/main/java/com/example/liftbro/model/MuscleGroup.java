package com.example.liftbro.model;

/**
 * Created by i57198 on 10/9/17.
 */

public class MuscleGroup {
    int mId;
    String mName;

    public MuscleGroup(int id, String name) {
        mId = id;
        mName = name;
    }

    public MuscleGroup(String name) {
        mName = name;
    }

    public void setId(int id) { mId = id; }
    public void setName(String name) { mName = name; }

    public int getId() { return mId; }
    public String getName() { return mName; }
}