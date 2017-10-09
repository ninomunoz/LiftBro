package com.example.liftbro.model;

/**
 * Created by i57198 on 10/9/17.
 */

public class Workout {
    int mId;
    String mName;

    public Workout(int id, String name) {
        mId = id;
        mName = name;
    }

    public Workout(String name) {
        mName = name;
    }

    public void setId(int id) { mId = id; }
    public void setName(String name) { mName = name; }

    public int getId() { return mId; }
    public String getName() { return mName; }
}
