package com.example.liftbro.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by i57198 on 10/30/17.
 */

public class Analytics {

    // Events
    private static final String EVENT_ADD_WORKOUT = "add_workout";
    private static final String EVENT_VIEW_WORKOUT = "view_workout";
    private static final String EVENT_START_TIMER = "start_timer";
    private static final String EVENT_INVITE = "invite";
    private static final String EVENT_VIEW_ABOUT = "view_about";
    private static final String EVENT_RENAME_WORKOUT = "rename_workout";
    private static final String EVENT_SHARE_WORKOUT = "share_workout";
    private static final String EVENT_DELETE_WORKOUT = "delete_workout";
    private static final String EVENT_ADD_EXERCISE = "add_exercise";

    // Params
    private static final String WORKOUT_ID = "workout_id";
    private static final String WORKOUT_NAME = "workout_name";

    public static void logEventAddWorkout(Context context, long workoutId, String workoutName) {
        Bundle params = new Bundle();
        params.putLong(WORKOUT_ID, workoutId);
        params.putString(WORKOUT_NAME, workoutName);
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_ADD_WORKOUT, params);
    }

    public static void logEventViewWorkout(Context context, long workoutId, String workoutName) {
        Bundle params = new Bundle();
        params.putLong(WORKOUT_ID, workoutId);
        params.putString(WORKOUT_NAME, workoutName);
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_VIEW_WORKOUT, params);
    }

    public static void logEventStartTimer(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_START_TIMER, null);
    }

    public static void logEventInvite(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_INVITE, null);
    }

    public static void logEventViewAbout(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_VIEW_ABOUT, null);
    }

    public static void logEventRenameWorkout(Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_RENAME_WORKOUT, null);
    }

    public static void logEventShareWorkout(Context context, long workoutId, String workoutName) {
        Bundle params = new Bundle();
        params.putLong(WORKOUT_ID, workoutId);
        params.putString(WORKOUT_NAME, workoutName);
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_SHARE_WORKOUT, params);
    }

    public static void logEventDeleteWorkout(Context context, long workoutId, String workoutName) {
        Bundle params = new Bundle();
        params.putLong(WORKOUT_ID, workoutId);
        params.putString(WORKOUT_NAME, workoutName);
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_DELETE_WORKOUT, params);
    }

    public static void logEventAddExercise(Context context, long exerciseId) {
        FirebaseAnalytics.getInstance(context).logEvent(EVENT_ADD_EXERCISE, null);
    }
}