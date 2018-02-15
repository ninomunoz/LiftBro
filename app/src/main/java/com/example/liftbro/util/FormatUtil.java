package com.example.liftbro.util;

import android.content.Context;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/21/17.
 */

public class FormatUtil {

    private static final String SPACE = " ";
    private static final String SETS_PREFIX = "S: ";
    private static final String REPS_PREFIX = "R: ";
    private static final String WEIGHT_PREFIX = "W: ";
    private static final String TIME_PREFIX = "T: ";

    public static String formatTime (Context context, int timeInSeconds, boolean withPrefix) {
        if (timeInSeconds >= 0 && timeInSeconds < 60) {
            return withPrefix ?
                    TIME_PREFIX + timeInSeconds + SPACE + context.getString(R.string.sec) :
                    timeInSeconds + SPACE + context.getString(R.string.sec);
        }
        else {
            int minutes = timeInSeconds / 60;
            int seconds = timeInSeconds % 60;

            if (seconds == 0) {
                return withPrefix ?
                        TIME_PREFIX + Integer.toString(minutes) + SPACE + context.getString(R.string.min) :
                        Integer.toString(minutes) + SPACE + context.getString(R.string.min);
            }
            else {
                return withPrefix ?
                        TIME_PREFIX + Integer.toString(minutes) + SPACE + context.getString(R.string.min) +
                                SPACE + seconds + SPACE + context.getString(R.string.sec) :
                        Integer.toString(minutes) + SPACE + context.getString(R.string.min) +
                                SPACE + seconds + SPACE + context.getString(R.string.sec);
            }
        }
    }

    public static String formatWeight(Context context, double weight, boolean withPrefix) {
        if (weight == 0.0) {
            return withPrefix ?
                    WEIGHT_PREFIX + context.getString(R.string.bw) :
                    context.getString(R.string.bw);
        }
        else {
            return withPrefix ?
                    WEIGHT_PREFIX + weight : // + SPACE + context.getString(R.string.lbs) :
                    String.valueOf(weight); // + SPACE + context.getString(R.string.lbs);
        }
    }

    public static String formatSets(int sets) {
        return SETS_PREFIX + sets;
    }

    public static String formatReps(int reps) {
        return REPS_PREFIX + reps;
    }

    public static String formatSetsContentDescription(Context context, int sets) {
        return sets + SPACE + context.getString(R.string.sets);
    }

    public static String formatRepsContentDescription(Context context, int reps) {
        return reps + SPACE + context.getString(R.string.reps);
    }

    public static String formatSharedExercise(Context context, String name, int sets, int reps, double weight, int time) {
        return time > 0 ?
                name + ": " + formatTime(context, time, false) :
                name + ": " + sets + "x" + reps + ", " + formatSharedWeight(context, weight);
    }

    private static String formatSharedWeight(Context context, double weight) {
        return weight + SPACE + context.getString(R.string.lbs);
    }
}