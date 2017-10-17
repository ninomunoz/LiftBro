package com.example.liftbro.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static com.example.liftbro.data.LiftContract.MuscleGroupEntry;
import static com.example.liftbro.data.LiftContract.WorkoutEntry;
import static com.example.liftbro.data.LiftContract.ExerciseEntry;
import static com.example.liftbro.data.LiftContract.WorkoutExerciseEntry;

/**
 * Created by i57198 on 10/9/17.
 */

public class LiftDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "liftdatabase.db";
    private static final int DATABASE_VERSION = 1;

    public LiftDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* MuscleGroup table */
    public Cursor getMuscleGroups(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(MuscleGroupEntry.TABLE_NAME);

        if (id != null) {
            sqLiteQueryBuilder.appendWhere("_id = " + id);
        }

        Cursor cursor = sqLiteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return cursor;
    }

    public long addMuscleGroup(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(MuscleGroupEntry.TABLE_NAME, "", values);

        if (id <= 0) {
            throw new SQLException("Failed to add movie");
        }

        return id;
    }

    public int deleteMuscleGroup(String id) {
        if (id == null) {
            return getWritableDatabase().delete(MuscleGroupEntry.TABLE_NAME, null, null);
        }
        else {
            return getWritableDatabase().delete(MuscleGroupEntry.TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateMuscleGroup(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }

    /* Workout table */
    public Cursor getWorkouts(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(WorkoutEntry.TABLE_NAME);

        if (id != null) {
            sqLiteQueryBuilder.appendWhere("_id = " + id);
        }

        Cursor cursor = sqLiteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return cursor;
    }

    public long addWorkout(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(MuscleGroupEntry.TABLE_NAME, "", values);

        if (id <= 0) {
            throw new SQLException("Failed to add movie");
        }

        return id;
    }

    public int deleteWorkout(String id) {
        if (id == null) {
            return getWritableDatabase().delete(MuscleGroupEntry.TABLE_NAME, null, null);
        }
        else {
            return getWritableDatabase().delete(MuscleGroupEntry.TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateWorkout(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }

    /* Exercise table */
    public Cursor getExercises(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(ExerciseEntry.TABLE_NAME);

        if (id != null) {
            sqLiteQueryBuilder.appendWhere("_id = " + id);
        }

        Cursor cursor = sqLiteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return cursor;
    }

    public long addExercise(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(ExerciseEntry.TABLE_NAME, "", values);

        if (id <= 0) {
            throw new SQLException("Failed to add movie");
        }

        return id;
    }

    public int deleteExercise(String id) {
        if (id == null) {
            return getWritableDatabase().delete(ExerciseEntry.TABLE_NAME, null, null);
        }
        else {
            return getWritableDatabase().delete(ExerciseEntry.TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateExercise(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(MuscleGroupEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }

    /* WorkoutExercise table */
    public Cursor getWorkoutExercises(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(WorkoutEntry.TABLE_NAME);

        if (id != null) {
            sqLiteQueryBuilder.appendWhere("_id = " + id);
        }

        Cursor cursor = sqLiteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        return cursor;
    }

    public long addWorkoutExercise(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(WorkoutExerciseEntry.TABLE_NAME, "", values);

        if (id <= 0) {
            throw new SQLException("Failed to add movie");
        }

        return id;
    }

    public int deleteWorkoutExercise(String id) {
        if (id == null) {
            return getWritableDatabase().delete(WorkoutExerciseEntry.TABLE_NAME, null, null);
        }
        else {
            return getWritableDatabase().delete(WorkoutExerciseEntry.TABLE_NAME, "_id=?", new String[]{id});
        }
    }

    public int updateWorkoutExercise(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(WorkoutExerciseEntry.TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(WorkoutExerciseEntry.TABLE_NAME, values, "_id=?", new String[]{id});
        }
    }
}