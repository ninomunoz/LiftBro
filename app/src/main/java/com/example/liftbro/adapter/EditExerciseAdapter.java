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

/**
 * Created by i57198 on 9/28/17.
 */

public class EditExerciseAdapter extends RecyclerView.Adapter<EditExerciseAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private View.OnClickListener mOnClickListener;

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
        mCursor.moveToPosition(position);

        final int exerciseId = mCursor.getInt(
                mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_EXERCISE_ID));

        String[] projection = { LiftContract.ExerciseEntry.COLUMN_NAME };
        String selection = LiftContract.ExerciseEntry._ID + " = ?";
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
                exerciseCursor.getColumnIndex(LiftContract.ExerciseEntry.COLUMN_NAME));
        final int sets = mCursor.getInt(
                mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_SETS));
        final int reps = mCursor.getInt(
                mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_REPS));
        final double weight = mCursor.getDouble(
                mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_WEIGHT));
        final int time = mCursor.getInt(
                mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry.COLUMN_TIME));

        // Set item views based on your views and data model
        TextView tvExerciseName = holder.tvExerciseName;
        TextView tvExerciseSet = holder.tvExerciseSet;
        TextView tvExerciseWeight = holder.tvExerciseWeight;

        tvExerciseName.setText(name);
        tvExerciseSet.setText(FormatUtil.formatSetsReps(sets, reps));

        if (time > 0) {
            tvExerciseWeight.setText(FormatUtil.formatTime(time));
        }
        else {
            tvExerciseWeight.setText(FormatUtil.formatWeight(weight));
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
                return mCursor.getLong(mCursor.getColumnIndex(LiftContract.WorkoutExerciseEntry._ID));
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

    public void setOnClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvExerciseName;
        public TextView tvExerciseSet;
        public TextView tvExerciseWeight;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tv_exercise_name);
            tvExerciseSet = itemView.findViewById(R.id.tv_exercise_set);
            tvExerciseWeight = itemView.findViewById(R.id.tv_exercise_weight);
        }
    }
}
