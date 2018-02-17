package com.example.liftbro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.R;
import com.example.liftbro.model.CompletedSet;
import com.example.liftbro.model.Exercise;
import com.example.liftbro.model.WorkoutExercise;
import com.example.liftbro.util.FormatUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 2/16/18.
 */

public class LiftAdapter extends RecyclerView.Adapter<LiftAdapter.LiftViewHolder> {

    Context mContext;
    List<WorkoutExercise> mTargetLifts;
    HashMap<WorkoutExercise, List<CompletedSet>> mLiftSetMap;

    public LiftAdapter(Context context) {
        mContext = context;
        mTargetLifts = new ArrayList<>();
        mLiftSetMap = new HashMap<>();
    }

    @Override
    public LiftAdapter.LiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lift, parent, false);
        return new LiftAdapter.LiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiftAdapter.LiftViewHolder holder, int position) {
        // Get the data model based on position
        final WorkoutExercise workoutExercise = mTargetLifts.get(position);
        final Exercise exercise = workoutExercise.getExercise();
        final String name = exercise.getName();
        final int targetSets = workoutExercise.getSets();
        final int targetReps = workoutExercise.getReps();
        final double targetWeight = workoutExercise.getWeight();
        final int targetTime = workoutExercise.getTime();
        boolean isTimedExercise = targetTime > 0;

        List<CompletedSet> completedSets = mLiftSetMap.get(workoutExercise);
        int completedTime = 0;
        List<Integer> completedReps = new ArrayList<>();
        List<Double> completedWeights = new ArrayList<>();

        String strCompletedReps = "", strCompletedTime = "", strCompletedWeights = "";

        if (completedSets != null) {
            for (CompletedSet set : completedSets) {
                if (isTimedExercise) {
                    completedTime = set.getTime();
                }
                else { // sets/reps workout
                    completedReps.add(set.getReps());
                    completedWeights.add(set.getWeight());
                }
            }
        }
        else { // no sets completed yet
            if (isTimedExercise) {
                strCompletedTime = "T: -";
            }
            else {
                strCompletedReps = "R: -";
                strCompletedWeights = "W: -";
                for (int i = 0; i < targetSets - 1; i++) {
                    strCompletedReps += "/-";
                    strCompletedWeights += "/-";
                }
            }
        }

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvTargetExerciseSet = holder.tvTargetExerciseSet;
        TextView tvTargetExerciseReps = holder.tvTargetExerciseReps;
        TextView tvTargetExerciseWeight = holder.tvTargetExerciseWeight;
        TextView tvCompletedExerciseSet = holder.tvCompletedExerciseSet;
        TextView tvCompletedExerciseReps = holder.tvCompletedExerciseReps;
        TextView tvCompletedExerciseWeight = holder.tvCompletedExerciseWeight;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        if (isTimedExercise) {
            tvTargetExerciseSet.setVisibility(View.GONE);
            tvTargetExerciseReps.setVisibility(View.GONE);
            tvTargetExerciseWeight.setText(FormatUtil.formatTime(mContext, targetTime, true));

            tvCompletedExerciseSet.setVisibility(View.GONE);
            tvCompletedExerciseReps.setVisibility(View.GONE);
            tvCompletedExerciseWeight.setText(strCompletedTime);
        }
        else {
            tvTargetExerciseSet.setText(FormatUtil.formatSets(targetSets));
            tvTargetExerciseReps.setText(FormatUtil.formatReps(targetReps));
            tvTargetExerciseWeight.setText(FormatUtil.formatWeight(mContext, targetWeight, true));

            tvCompletedExerciseSet.setText(FormatUtil.formatSets(completedSets != null ? completedSets.size() : 0));
            tvCompletedExerciseReps.setText(strCompletedReps);
            tvCompletedExerciseWeight.setText(strCompletedWeights);
        }
    }

    @Override
    public int getItemCount() {
        return mTargetLifts != null ? mTargetLifts.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mTargetLifts != null) {
            return mTargetLifts.get(position).getId();
        }
        return 0;
    }

    public void setWorkoutExercises(List<WorkoutExercise> data) {
        mTargetLifts = data;
        notifyDataSetChanged();
    }

    public class LiftViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_exercise_name) public TextView tvExerciseName;
        @BindView(R.id.tv_completed_exercise_set) public TextView tvCompletedExerciseSet;
        @BindView(R.id.tv_completed_exercise_reps) public TextView tvCompletedExerciseReps;
        @BindView(R.id.tv_completed_exercise_weight) public TextView tvCompletedExerciseWeight;
        @BindView(R.id.tv_target_exercise_set) public TextView tvTargetExerciseSet;
        @BindView(R.id.tv_target_exercise_reps) public TextView tvTargetExerciseReps;
        @BindView(R.id.tv_target_exercise_weight) public TextView tvTargetExerciseWeight;

        public LiftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
