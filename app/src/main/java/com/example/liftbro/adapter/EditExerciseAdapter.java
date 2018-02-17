package com.example.liftbro.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liftbro.R;
import com.example.liftbro.data.LiftContract;
import com.example.liftbro.helper.ExerciseTouchHelper;
import com.example.liftbro.model.Exercise;
import com.example.liftbro.model.WorkoutExercise;
import com.example.liftbro.util.FormatUtil;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 9/28/17.
 */

public class EditExerciseAdapter extends RecyclerView.Adapter<EditExerciseAdapter.ViewHolder>
        implements ExerciseTouchHelper.ExerciseTouchListener {

    private Context mContext;
    private View.OnClickListener mOnClickListener;
    List<WorkoutExercise> mWorkoutExercises;
    ExerciseTouchHelper.OnStartDragListener mDragStartListener;

    public EditExerciseAdapter(Context context, ExerciseTouchHelper.OnStartDragListener listener) {
        mContext = context;
        mDragStartListener = listener;
    }

    @Override
    public EditExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_exercise, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new EditExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EditExerciseAdapter.ViewHolder holder, int position) {
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
        TextView tvExerciseReps = holder.tvExerciseReps;
        TextView tvExerciseWeight = holder.tvExerciseWeight;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        if (time > 0) {
            tvExerciseSet.setVisibility(View.GONE);
            tvExerciseReps.setVisibility(View.GONE);

            String timeText = FormatUtil.formatTime(mContext, time, true);
            tvExerciseWeight.setText(timeText);
            tvExerciseWeight.setContentDescription(timeText);
        }
        else {
            tvExerciseSet.setText(FormatUtil.formatSets(sets));
            tvExerciseSet.setContentDescription(FormatUtil.formatSetsContentDescription(mContext, sets));
            tvExerciseReps.setText(FormatUtil.formatReps(reps));
            tvExerciseReps.setContentDescription(FormatUtil.formatRepsContentDescription(mContext, reps));
            String weightText = FormatUtil.formatWeight(mContext, weight, true);
            tvExerciseWeight.setText(weightText);
            tvExerciseWeight.setContentDescription(weightText);
        }

        holder.ivHandle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
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

    @Override
    public void onItemSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        long id = mWorkoutExercises.get(position).getId();
        mContext.getContentResolver().delete(
                ContentUris.withAppendedId(LiftContract.WorkoutExerciseEntry.CONTENT_URI, id),
                null, null);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mWorkoutExercises, i, i+1);
            }
        }
        else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mWorkoutExercises, i, i-1);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_exercise_name) public TextView tvExerciseName;
        @BindView(R.id.tv_completed_exercise_set) public TextView tvExerciseSet;
        @BindView(R.id.tv_completed_exercise_reps) public TextView tvExerciseReps;
        @BindView(R.id.tv_completed_exercise_weight) public TextView tvExerciseWeight;
        @BindView(R.id.view_background) public RelativeLayout viewBackground;
        @BindView(R.id.view_foreground) public RelativeLayout viewForeground;
        @BindView(R.id.handle) public ImageView ivHandle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
