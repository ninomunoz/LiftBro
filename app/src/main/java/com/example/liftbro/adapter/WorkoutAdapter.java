package com.example.liftbro.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.activity.MainActivity;
import com.example.liftbro.R;
import com.example.liftbro.fragment.WorkoutDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.liftbro.data.LiftContract.WorkoutEntry;

/**
 * Created by i57198 on 9/16/17.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    Context mContext;
    Cursor mCursor;

    public WorkoutAdapter(Context context) {
        mContext = context;
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        final int workoutId = mCursor.getInt(mCursor.getColumnIndex((WorkoutEntry._ID)));
        final String workoutName = mCursor.getString(mCursor.getColumnIndex(WorkoutEntry.COLUMN_NAME));

        holder.workoutName.setText(workoutName);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDetailFragment newFragment = WorkoutDetailFragment.newInstance(workoutId, workoutName);
                FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getLong(mCursor.getColumnIndex(WorkoutEntry._ID));
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

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_workout) CardView cardView;
        @BindView(R.id.tv_workout_name) TextView workoutName;

        WorkoutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}