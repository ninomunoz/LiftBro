package com.example.liftbro.data;

import android.content.ContentProvider;
import android.content.ContentUris;
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

    private DatabaseHelper mDatabase = null;

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
        mDatabase = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case MUSCLE_GROUPS:
                return MuscleGroupEntry.CONTENT_TYPE;
            case MUSCLE_GROUP_ID:
                return MuscleGroupEntry.CONTENT_ITEM_TYPE;
            case WORKOUTS:
                return WorkoutEntry.CONTENT_TYPE;
            case WORKOUT_ID:
                return WorkoutEntry.CONTENT_ITEM_TYPE;
            case EXERCISES:
                return ExerciseEntry.CONTENT_TYPE;
            case EXERCISE_ID:
                return ExerciseEntry.CONTENT_ITEM_TYPE;
            case WORKOUT_EXERCISES:
                return WorkoutExerciseEntry.CONTENT_TYPE;
            case WORKOUT_EXERCISE_ID:
                return WorkoutExerciseEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        Cursor cursor = null;

        switch (mUriMatcher.match(uri)) {
            case MUSCLE_GROUPS:
                cursor = mDatabase.getMuscleGroups(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case MUSCLE_GROUP_ID:
                id = uri.getPathSegments().get(1);
                cursor = mDatabase.getMuscleGroups(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case WORKOUTS:
                cursor = mDatabase.getWorkouts(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case WORKOUT_ID:
                id = uri.getPathSegments().get(1);
                cursor = mDatabase.getWorkouts(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case EXERCISES:
                cursor = mDatabase.getExercises(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                cursor = mDatabase.getExercises(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case WORKOUT_EXERCISES:
                cursor = mDatabase.getWorkoutExercises(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case WORKOUT_EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                cursor = mDatabase.getWorkoutExercises(id, projection, selection, selectionArgs, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id;

        switch (mUriMatcher.match(uri)) {
            case MUSCLE_GROUPS:
                id = mDatabase.addMuscleGroup(contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(MuscleGroupEntry.CONTENT_URI, id);
            case WORKOUTS:
                id = mDatabase.addWorkout(contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(WorkoutEntry.CONTENT_URI, id);
            case EXERCISES:
                id = mDatabase.addExercise(contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(ExerciseEntry.CONTENT_URI, id);
            case WORKOUT_EXERCISES:
                id = mDatabase.addWorkoutExercise(contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(WorkoutExerciseEntry.CONTENT_URI, id);
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        int deletedCount = 0;

        switch (mUriMatcher.match(uri)) {
            case MUSCLE_GROUPS:
                deletedCount = mDatabase.deleteMuscleGroup(id);
                break;
            case MUSCLE_GROUP_ID:
                id = uri.getPathSegments().get(1);
                deletedCount = mDatabase.deleteMuscleGroup(id);
                break;
            case WORKOUTS:
                deletedCount = mDatabase.deleteWorkout(id);
                break;
            case WORKOUT_ID:
                id = uri.getPathSegments().get(1);
                deletedCount = mDatabase.deleteWorkout(id);
                break;
            case EXERCISES:
                deletedCount = mDatabase.deleteExercise(id);
                break;
            case EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                deletedCount = mDatabase.deleteExercise(id);
                break;
            case WORKOUT_EXERCISES:
                deletedCount = mDatabase.deleteWorkoutExercise(id);
                break;
            case WORKOUT_EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                deletedCount = mDatabase.deleteWorkoutExercise(id);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return deletedCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        String id = null;
        int updatedCount = 0;

        switch (mUriMatcher.match(uri)) {
            case MUSCLE_GROUPS:
                updatedCount = mDatabase.updateMuscleGroup(id, contentValues);
                break;
            case MUSCLE_GROUP_ID:
                id = uri.getPathSegments().get(1);
                updatedCount = mDatabase.updateMuscleGroup(id, contentValues);
                break;
            case WORKOUTS:
                updatedCount = mDatabase.updateWorkout(id, contentValues);
                break;
            case WORKOUT_ID:
                id = uri.getPathSegments().get(1);
                updatedCount = mDatabase.updateWorkout(id, contentValues);
                break;
            case EXERCISES
                updatedCount = mDatabase.updateExercise(id, contentValues);
                break;
            case EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                updatedCount = mDatabase.updateExercise(id, contentValues);
                break;
            case WORKOUT_EXERCISES:
                updatedCount = mDatabase.updateWorkoutExercise(id, contentValues);
                break;
            case WORKOUT_EXERCISE_ID:
                id = uri.getPathSegments().get(1);
                updatedCount = mDatabase.updateWorkoutExercise(id, contentValues);
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return updatedCount;
    }
}
