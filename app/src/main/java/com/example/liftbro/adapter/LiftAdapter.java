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
        boolean isTimedExercise = workoutExercise.getTime() > 0;

        List<CompletedSet> completedSets = mLiftSetMap.get(workoutExercise);
        int sets = workoutExercise.getSets();
        int time = 0;
        List<Integer> reps = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        String strReps = "", strTime = "", strWeights = "";

        if (completedSets != null) {
            for (CompletedSet set : completedSets) {
                if (isTimedExercise) {
                    time = set.getTime();
                }
                else { // sets/reps workout
                    reps.add(set.getReps());
                    weights.add(set.getWeight());
                }
            }
        }
        else { // no sets completed yet
            if (isTimedExercise) {
                strTime = "T: -";
            }
            else {
                strReps = "R: -";
                strWeights = "W: -";
                for (int i = 0; i < sets - 1; i++) {
                    strReps += "/-";
                    strWeights += "/-";
                }
            }
        }

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvExerciseSet = holder.tvExerciseSet;
        TextView tvExerciseReps = holder.tvExerciseReps;
        TextView tvExerciseWeight = holder.tvExerciseWeight;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        if (isTimedExercise) {
            tvExerciseSet.setVisibility(View.GONE);
            tvExerciseReps.setVisibility(View.GONE);
            tvExerciseWeight.setText(strTime);
        }
        else {
            tvExerciseSet.setText(FormatUtil.formatSets(sets));
            tvExerciseReps.setText(strReps);
            tvExerciseWeight.setText(strWeights);
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
        @BindView(R.id.tv_exercise_set) public TextView tvExerciseSet;
        @BindView(R.id.tv_exercise_reps) public TextView tvExerciseReps;
        @BindView(R.id.tv_exercise_weight) public TextView tvExerciseWeight;

        public LiftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
