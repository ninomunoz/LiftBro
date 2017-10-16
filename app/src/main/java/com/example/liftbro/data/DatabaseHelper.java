package com.example.liftbro.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

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