package com.example.liftbro.util;

import android.content.Context;

import com.example.liftbro.R;

/**
 * Created by i57198 on 10/21/17.
 */

public class FormatUtil {

    private static final String SPACE = " ";

    public static String formatTime (Context context, int timeInSeconds) {
        if (timeInSeconds >= 0 && timeInSeconds < 60) {
            return timeInSeconds + SPACE + context.getString(R.string.sec);
        }
        else {
            int minutes = timeInSeconds / 60;
            int seconds = timeInSeconds % 60;

            if (seconds == 0) {
                return Integer.toString(minutes) + SPACE + context.getString(R.string.min);
            }
            else {
                return Integer.toString(minutes) + SPACE + context.getString(R.string.min) +
                        SPACE + seconds + SPACE + context.getString(R.string.sec);
            }
        }
    }

    public static String formatWeight(Context context, double weight) {
        return weight == 0.0 ?
                context.getString(R.string.bw) :
                weight + SPACE + context.getString(R.string.lbs);
    }

    public static String formatSetsReps(Context context, int sets, int reps) {
        String formattedSetsReps;
        if (sets > 0) {
            formattedSetsReps = sets + SPACE + context.getString(R.string.sets);
            if (reps > 0) {
                formattedSetsReps = sets + " x " + reps;
            }
        }
        else {
            formattedSetsReps = context.getString(R.string.no_sets_reps);
        }

        return formattedSetsReps;
    }

    public static String formatSetsRepsContentDescription(Context context, int sets, int reps) {
        String contentDescription = "";
        if (sets > 0) {
            contentDescription = sets + SPACE + context.getString(R.string.sets) + SPACE;
            if (reps > 0) {
                contentDescription += + reps + SPACE + context.getString(R.string.reps);
            }
        }

        return contentDescription;
    }
}