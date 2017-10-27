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
        RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    Context mContext;
    Cursor mCursor;

    public ExerciseAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
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

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_exercise_name) public TextView tvExerciseName;
        @BindView(R.id.tv_exercise_set) public TextView tvExerciseSet;
        @BindView(R.id.tv_exercise_weight) public TextView tvExerciseWeight;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
