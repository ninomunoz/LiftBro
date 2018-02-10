package com.example.liftbro.adapter;

import android.content.Context;
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
import com.example.liftbro.helper.WorkoutMoveHelper;
import com.example.liftbro.model.Workout;
import com.example.liftbro.util.Analytics;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i57198 on 9/16/17.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> implements WorkoutMoveHelper.WorkoutMoveListener{

    Context mContext;
    List<Workout> mWorkouts;

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
        final Workout workout = mWorkouts.get(position);
        holder.workoutName.setText(workout.getName());

        holder.cardView.setContentDescription(workout.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDetailFragment newFragment = WorkoutDetailFragment.newInstance(workout.getId(), workout.getName());
                FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, newFragment);

                // Up navigation not necessary if two pane
                boolean isTwoPane = mContext.getResources().getBoolean(R.bool.is_two_pane);
                if (!isTwoPane) {
                    transaction.addToBackStack(null);
                }

                transaction.commit();

                Analytics.logEventViewWorkout(mContext, workout.getId(), workout.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorkouts != null ? mWorkouts.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        if (mWorkouts != null) {
            return mWorkouts.get(position).getId();
        }
        return 0;
    }

    // WorkoutMoveHelper.WorkoutMoveListener implementation
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mWorkouts, i, i+1);
            }
        }
        else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mWorkouts, i, i-1);
                mWorkouts.get(i).setPosition(i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setWorkouts(List<Workout> data) {
        mWorkouts = data;
        notifyDataSetChanged();
    }

    public List<Workout> getWorkouts() {
        return mWorkouts;
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