package com.example.liftbro.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by i57198 on 10/5/17.
 */

public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.example.liftbro";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /* MuscleGroup table */
    public static final class MuscleGroupEntry implements BaseColumns {

        public static final String TABLE_NAME = "musclegroup";
        public static final String COLUMN_NAME = "name";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(MuscleGroupEntry.TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                // vnd.android.cursor.dir/vnd.com.example.liftbro.provider.musclegroup
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MuscleGroupEntry.TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                // vnd.android.cursor.item/vnd.com.example.liftbro.provider.musclegroup
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MuscleGroupEntry.TABLE_NAME;
    }

    /* Workout table */
    public static final class WorkoutEntry implements BaseColumns {

        public static final String TABLE_NAME = "workout";
        public static final String COLUMN_NAME = "name";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(WorkoutEntry.TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                // vnd.android.cursor.dir/vnd.com.example.liftbro.provider.workout
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + WorkoutEntry.TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                // vnd.android.cursor.item/vnd.com.example.liftbro.provider.workout
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + WorkoutEntry.TABLE_NAME;
    }

    /* Exercise table */
    public static final class ExerciseEntry implements BaseColumns {

        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MUSCLEGROUP_ID = "musclegroupid";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(ExerciseEntry.TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                // vnd.android.cursor.dir/vnd.com.example.liftbro.provider.exercise
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ExerciseEntry.TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                // vnd.android.cursor.item/vnd.com.example.liftbro.provider.exercise
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + ExerciseEntry.TABLE_NAME;
    }

    /* WorkoutExercise table */
    public static final class WorkoutExerciseEntry implements BaseColumns {

        public static final String TABLE_NAME = "workoutexercise";
        public static final String COLUMN_WORKOUT_ID = "workoutid";
        public static final String COLUMN_EXERCISE_ID = "exerciseid";
        public static final String COLUMN_SETS = "sets";
        public static final String COLUMN_REPS = "reps";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_TIME = "time";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(WorkoutExerciseEntry.TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                // vnd.android.cursor.dir/vnd.com.example.liftbro.provider.workoutexercise
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + WorkoutExerciseEntry.TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                // vnd.android.cursor.item/vnd.com.example.liftbro.provider.workoutexercise
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + WorkoutExerciseEntry.TABLE_NAME;
    }
}
