package com.example.liftbro.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.liftbro.data.DatabaseContract.MuscleGroupEntry;
import static com.example.liftbro.data.DatabaseContract.WorkoutEntry;
import static com.example.liftbro.data.DatabaseContract.ExerciseEntry;
import static com.example.liftbro.data.DatabaseContract.WorkoutExerciseEntry;

/**
 * Created by i57198 on 10/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LiftBroDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MUSCLE_GROUP =
            "CREATE TABLE " + MuscleGroupEntry.TABLE_NAME + " (" +
            MuscleGroupEntry._ID + " INTEGER PRIMARY KEY, " +
            MuscleGroupEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_WORKOUT =
            "CREATE TABLE "  + WorkoutEntry.TABLE_NAME + " (" +
                    WorkoutEntry._ID + " INTEGER PRIMARY KEY, " +
                    WorkoutEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_EXERCISE =
            "CREATE TABLE "  + ExerciseEntry.TABLE_NAME + " (" +
                    ExerciseEntry._ID + " INTEGER PRIMARY KEY, " +
                    ExerciseEntry.COLUMN_MUSCLEGROUP_ID + " INTEGER, " +
                    ExerciseEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_CREATE_TABLE_WORKOUT_EXERCISE =
            "CREATE TABLE "  + WorkoutExerciseEntry.TABLE_NAME + " (" +
                    WorkoutExerciseEntry._ID + " INTEGER PRIMARY KEY, " +
                    WorkoutExerciseEntry.COLUMN_WORKOUT_ID + " INTEGER, " +
                    WorkoutExerciseEntry.COLUMN_EXERCISE_ID + " INTEGER, " +
                    WorkoutExerciseEntry.COLUMN_SETS + " INTEGER, " +
                    WorkoutExerciseEntry.COLUMN_REPS + " INTEGER, " +
                    WorkoutExerciseEntry.COLUMN_WEIGHT + " REAL, " +
                    WorkoutExerciseEntry.COLUMN_TIME + " INTEGER)";

    private static final String SQL_DROP_TABLE_MUSCLE_GROUP =
            "DROP TABLE IF EXISTS " + MuscleGroupEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_WORKOUT =
            "DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_EXERCISE =
            "DROP TABLE IF EXISTS " + ExerciseEntry.TABLE_NAME;

    private static final String SQL_DROP_TABLE_WORKOUT_EXERCISE =
            "DROP TABLE IF EXISTS" + WorkoutExerciseEntry.TABLE_NAME;

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

        onCreate(db);
    }
}