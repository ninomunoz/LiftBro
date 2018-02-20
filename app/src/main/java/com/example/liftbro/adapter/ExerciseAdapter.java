package com.example.liftbro.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.data.LiftContract;
import com.example.liftbro.R;
import com.example.liftbro.util.FormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.liftbro.data.LiftContract.WorkoutExerciseEntry;
import static com.example.liftbro.data.LiftContract.ExerciseEntry;

/**
 * Created by i57198 on 9/17/17.
 */

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public ExerciseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        mCursor.moveToPosition(position);

        final int exerciseId = mCursor.getInt(
                mCursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_EXERCISE_ID));

        String[] projection = { ExerciseEntry.COLUMN_NAME };
        String selection = ExerciseEntry._ID + " = ?";
        String[] selectionArgs = { Integer.toString(exerciseId) };

        Cursor exerciseCursor = mContext.getContentResolver().query(
                LiftContract.ExerciseEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        exerciseCursor.moveToFirst();

        final String name = exerciseCursor.getString(
                exerciseCursor.getColumnIndex(ExerciseEntry.COLUMN_NAME));
        final int sets = mCursor.getInt(
                mCursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_SETS));
        final int reps = mCursor.getInt(
                mCursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_REPS));
        final double weight = mCursor.getDouble(
                mCursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_WEIGHT));
        final int time = mCursor.getInt(
                mCursor.getColumnIndex(WorkoutExerciseEntry.COLUMN_TIME));

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvTargetSets = holder.tvTargetSets;
        TextView tvTargetReps = holder.tvTargetReps;
        TextView tvTargetWeightTime = holder.tvTargetWeightTime;

        tvExerciseName.setText(name);
        tvExerciseName.setContentDescription(name);

        if (time > 0) {
            tvTargetSets.setVisibility(View.GONE);
            tvTargetReps.setVisibility(View.GONE);

            String timeText = FormatUtil.formatTime(mContext, time, true);
            tvTargetWeightTime.setText(timeText);
            tvTargetWeightTime.setContentDescription(timeText);
        }
        else {
            tvTargetSets.setText(FormatUtil.formatSets(sets));
            tvTargetSets.setContentDescription(FormatUtil.formatSetsContentDescription(mContext, sets));
            tvTargetReps.setText(FormatUtil.formatReps(reps));
            tvTargetReps.setContentDescription(FormatUtil.formatRepsContentDescription(mContext, reps));

            String weightText = FormatUtil.formatWeight(mContext, weight, true);
            tvTargetWeightTime.setText(weightText);
            tvTargetWeightTime.setContentDescription(weightText);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getLong(mCursor.getColumnIndex(WorkoutExerciseEntry._ID));
            }
            return 0;
        }
        return 0;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_exercise_name) public TextView tvExerciseName;
        @BindView(R.id.tv_last_sets) public TextView tvLastSets;
        @BindView(R.id.tv_last_reps) public TextView tvLastReps;
        @BindView(R.id.tv_last_weight_time) public TextView tvLastWeightTime;
        @BindView(R.id.tv_target_sets) public TextView tvTargetSets;
        @BindView(R.id.tv_target_reps) public TextView tvTargetReps;
        @BindView(R.id.tv_target_weight_time) public TextView tvTargetWeightTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
