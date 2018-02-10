package com.example.liftbro.model;

/**
 * Created by i57198 on 10/9/17.
 */

public class Workout {
    int mId;
    String mName;
    int mPosition;

    public Workout(int id, String name, int position) {
        mId = id;
        mName = name;
        mPosition = position;
    }

    public Workout(String name, int position) {
        mName = name;
        mPosition = position;
    }

    public void setId(int id) { mId = id; }
    public void setName(String name) { mName = name; }
    public void setPosition(int position) { mPosition = position; }

    public int getId() { return mId; }
    public String getName() { return mName; }
    public int getPosition() { return mPosition; }
}
