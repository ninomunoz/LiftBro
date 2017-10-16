package com.example.liftbro.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.liftbro.data.DatabaseContract.MuscleGroupEntry;
import static com.example.liftbro.data.DatabaseContract.WorkoutEntry;
import static com.example.liftbro.data.DatabaseContract.ExerciseEntry;
import static com.example.liftbro.data.DatabaseContract.WorkoutExerciseEntry;

/**
 * Created by i57198 on 10/15/17.
 */

public class DatabaseProvider extends ContentProvider {

    private static final int MUSCLE_GROUPS = 1;
    private static final int MUSCLE_GROUP_ID = 2;
    private static final int WORKOUTS = 3;
    private static final int WORKOUT_ID=  4;
    private static final int EXERCISES = 5;
    private static final int EXERCISE_ID = 6;
    private static final int WORKOUT_EXERCISES = 7;
    private static final int WORKOUT_EXERCISE_ID = 8;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, MuscleGroupEntry.TABLE_NAME, MUSCLE_GROUPS);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, MuscleGroupEntry.TABLE_NAME + "/#", MUSCLE_GROUP_ID);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, WorkoutEntry.TABLE_NAME, WORKOUTS);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, WorkoutEntry.TABLE_NAME + "/#", WORKOUT_ID);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, ExerciseEntry.TABLE_NAME, EXERCISES);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, ExerciseEntry.TABLE_NAME + "/#", EXERCISE_ID);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, WorkoutExerciseEntry.TABLE_NAME, WORKOUT_EXERCISES);
        mUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, WorkoutExerciseEntry.TABLE_NAME + "/#", WORKOUT_EXERCISE_ID);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
