package com.example.liftbro.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liftbro.MainActivity;
import com.example.liftbro.R;
import com.example.liftbro.fragment.WorkoutDetailFragment;

import java.util.List;

/**
 * Created by i57198 on 9/16/17.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    List<String> mWorkouts;
    Context mContext;

    public WorkoutAdapter(Context context, List<String> workouts) {
        mContext = context;
        mWorkouts = workouts;
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        final String workoutName = mWorkouts.get(position);
        holder.workoutName.setText(workoutName);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutDetailFragment newFragment = WorkoutDetailFragment.newInstance(workoutName);
                FragmentTransaction transaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView workoutName;

        WorkoutViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv_workout);
            workoutName = (TextView)itemView.findViewById(R.id.tv_workout_name);
        }
    }
}