package com.example.liftbro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liftbro.R;
import com.example.liftbro.model.Exercise;
import com.example.liftbro.model.WorkoutExercise;
import com.example.liftbro.util.FormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 9/28/17.
 */

public class EditExerciseAdapter extends RecyclerView.Adapter<EditExerciseAdapter.ViewHolder> {

    private Context mContext;
    private View.OnClickListener mOnClickListener;
    List<WorkoutExercise> mWorkoutExercises;

    public EditExerciseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public EditExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_exercise, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new EditExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditExerciseAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        final WorkoutExercise workoutExercise = mWorkoutExercises.get(position);
        final Exercise exercise = workoutExercise.getExercise();
        final String name = exercise.getName();
        final int sets = workoutExercise.getSets();
        final int reps = workoutExercise.getReps();
        final double weight = workoutExercise.getWeight();
        final int time = workoutExercise.getTime();

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvExerciseSet = holder.tvExerciseSet;
        TextView tvExerciseWeight = holder.tvExerciseWeight;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        tvExerciseSet.setText(FormatUtil.formatSetsReps(mContext, sets, reps));
        tvExerciseSet.setContentDescription(FormatUtil.formatSetsRepsContentDescription(mContext, sets, reps));

        if (time > 0) {
            String timeText = FormatUtil.formatTime(mContext, time);
            tvExerciseWeight.setText(timeText);
            tvExerciseWeight.setContentDescription(timeText);
        }
        else {
            String weightText = FormatUtil.formatWeight(mContext, weight);
            tvExerciseWeight.setText(weightText);
            tvExerciseWeight.setContentDescription(weightText);
        }
    }

    @Override
    public int getItemCount() {
        return mWorkoutExercises != null ? mWorkoutExercises.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mWorkoutExercises != null) {
            return mWorkoutExercises.get(position).getId();
        }
        return 0;
    }

    public void setWorkoutExercises(List<WorkoutExercise> data) {
        mWorkoutExercises = data;
        notifyDataSetChanged();
    }

    public List<WorkoutExercise> getWorkoutExercises() {
        return mWorkoutExercises;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_exercise_name) public TextView tvExerciseName;
        @BindView(R.id.tv_exercise_set) public TextView tvExerciseSet;
        @BindView(R.id.tv_exercise_weight) public TextView tvExerciseWeight;
        @BindView(R.id.view_background) public RelativeLayout viewBackground;
        @BindView(R.id.view_foreground) public RelativeLayout viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
