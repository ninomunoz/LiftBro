package com.example.liftbro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by i57198 on 10/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LiftBroDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MUSCLE_GROUP =
            "CREATE TABLE " + DatabaseContract.MuscleGroupEntry.TABLE_NAME + " (" +
            DatabaseContract.MuscleGroupEntry._ID + " INTEGER PRIMARY KEY, " +
            DatabaseContract.MuscleGroupEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_WORKOUT =
            "CREATE TABLE "  + DatabaseContract.WorkoutEntry.TABLE_NAME + " (" +
                    DatabaseContract.WorkoutEntry._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.WorkoutEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_EXERCISE =
            "CREATE TABLE "  + DatabaseContract.ExerciseEntry.TABLE_NAME + " (" +
                    DatabaseContract.ExerciseEntry._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.ExerciseEntry.COLUMN_MUSCLEGROUP_ID + " INTEGER, " +
                    DatabaseContract.ExerciseEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_WORKOUT_EXERCISE =
            "CREATE TABLE "  + DatabaseContract.WorkoutExerciseEntry.TABLE_NAME + " (" +
                    DatabaseContract.WorkoutExerciseEntry._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_WORKOUT_ID + " INTEGER, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_EXERCISE_ID + " INTEGER, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_SETS + " INTEGER, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_REPS + " INTEGER, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_WEIGHT + " REAL, " +
                    DatabaseContract.WorkoutExerciseEntry.COLUMN_TIME + " INTEGER)";

    private static final String SQL_DROP_TABLE_MUSCLE_GROUP =
            "DROP TABLE IF EXISTS " + DatabaseContract.MuscleGroupEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_WORKOUT =
            "DROP TABLE IF EXISTS " + DatabaseContract.WorkoutEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_EXERCISE =
            "DROP TABLE IF EXISTS " + DatabaseContract.ExerciseEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_WORKOUT_EXERCISE =
            "DROP TABLE IF EXISTS" + DatabaseContract.WorkoutExerciseEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MUSCLE_GROUP);
        db.execSQL(SQL_CREATE_TABLE_WORKOUT);
        db.execSQL(SQL_CREATE_TABLE_EXERCISE);
        db.execSQL(SQL_CREATE_TABLE_WORKOUT_EXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_MUSCLE_GROUP);
        db.execSQL(SQL_DROP_TABLE_WORKOUT);
        db.execSQL(SQL_DROP_TABLE_EXERCISE);
        db.execSQL(SQL_DROP_TABLE_WORKOUT_EXERCISE);
    }
}