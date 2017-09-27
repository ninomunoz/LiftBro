package com.example.liftbro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.Exercise;
import com.example.liftbro.R;

import java.util.List;

/**
 * Created by i57198 on 9/17/17.
 */

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private List<Exercise> mExercises;
    private Context mContext;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        mContext = context;
        mExercises = exercises;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View exerciseView = inflater.inflate(R.layout.item_exercise, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(exerciseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Exercise exercise = mExercises.get(position);
        int sets = exercise.getSets();
        int reps = exercise.getReps();
        double weight = exercise.getWeight();
        int time = exercise.getTime();

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvExerciseSet = holder.tvExerciseSet;
        TextView tvExerciseWeight = holder.tvExerciseWeight;

        tvExerciseName.setText(exercise.getName());
        tvExerciseSet.setText(formatSetsReps(sets, reps));

        if (time > 0) {
            tvExerciseWeight.setText(formatTime(time));
        }
        else {
            tvExerciseWeight.setText(formatWeight(weight));
        }
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    private String formatTime(int timeInSeconds) {
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

    private String formatWeight(double weight) {
        return weight == 0.0 ? "BW" : weight + " lb";
    }

    private String formatSetsReps(int sets, int reps) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvExerciseName;
        public TextView tvExerciseSet;
        public TextView tvExerciseWeight;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExerciseName = (TextView) itemView.findViewById(R.id.tv_exercise_name);
            tvExerciseSet = (TextView) itemView.findViewById(R.id.tv_exercise_set);
            tvExerciseWeight = (TextView) itemView.findViewById(R.id.tv_exercise_weight);
        }
    }

}
