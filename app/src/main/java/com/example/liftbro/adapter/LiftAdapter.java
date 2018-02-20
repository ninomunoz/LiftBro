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
        TextView tvTargetSets = holder.tvTargetSets;
        TextView tvTargetReps = holder.tvTargetReps;
        TextView tvTargetWeightTime = holder.tvTargetWeightTime;
        TextView tvActualSets = holder.tvActualSets;
        TextView tvActualReps = holder.tvActualReps;
        TextView tvActualWeightTime = holder.tvActualWeightTime;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        if (isTimedExercise) {
            tvTargetSets.setVisibility(View.GONE);
            tvTargetReps.setVisibility(View.GONE);
            tvTargetWeightTime.setText(FormatUtil.formatTime(mContext, targetTime, true));

            tvActualSets.setVisibility(View.GONE);
            tvActualReps.setVisibility(View.GONE);
            tvActualWeightTime.setText(strCompletedTime);
        }
        else {
            tvTargetSets.setText(FormatUtil.formatSets(targetSets));
            tvTargetReps.setText(FormatUtil.formatReps(targetReps));
            tvTargetWeightTime.setText(FormatUtil.formatWeight(mContext, targetWeight, true));

            tvActualSets.setText(FormatUtil.formatSets(completedSets != null ? completedSets.size() : 0));
            tvActualReps.setText(strCompletedReps);
            tvActualWeightTime.setText(strCompletedWeights);
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
        @BindView(R.id.tv_actual_sets) public TextView tvActualSets;
        @BindView(R.id.tv_actual_reps) public TextView tvActualReps;
        @BindView(R.id.tv_actual_weight_time) public TextView tvActualWeightTime;
        @BindView(R.id.tv_target_sets) public TextView tvTargetSets;
        @BindView(R.id.tv_target_reps) public TextView tvTargetReps;
        @BindView(R.id.tv_target_weight_time) public TextView tvTargetWeightTime;

        public LiftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
