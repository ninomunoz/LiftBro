package com.example.liftbro.util;

/**
 * Created by i57198 on 10/21/17.
 */

public class FormatUtil {

    public static String formatTime (int timeInSeconds) {
        if (timeInSeconds >= 0 && timeInSeconds < 60) {
            return timeInSeconds + " sec";
        }
        else {
            int minutes = timeInSeconds / 60;
            int seconds = timeInSeconds % 60;

            if (seconds == 0) {
                return Integer.toString(minutes) + " min";
            }
            else {
                return Integer.toString(minutes) + " min " + seconds + " sec";
            }
        }
    }

    public static String formatWeight(double weight) {
        return weight == 0.0 ? "BW" : weight + " lb";
    }

    public static String formatSetsReps(int sets, int reps) {
        String formattedSetsReps;
        if (sets > 0) {
            formattedSetsReps = sets + " sets";
            if (reps > 0) {
                formattedSetsReps = sets + " x " + reps;
            }
        }
        else {
            formattedSetsReps = "---";
        }

        return formattedSetsReps;
    }
}
