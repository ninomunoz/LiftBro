package com.example.liftbro.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.liftbro.adapter.EditExerciseAdapter;

/**
 * Created by i57198 on 10/23/17.
 */

public class ExerciseTouchHelper extends ItemTouchHelper.Callback {

    private ExerciseTouchListener mListener;

    public ExerciseTouchHelper(ExerciseTouchListener listener) {
        mListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                ((EditExerciseAdapter.ViewHolder)viewHolder).viewBackground.setVisibility(View.VISIBLE);
                final View foregroundView = ((EditExerciseAdapter.ViewHolder)viewHolder).viewForeground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
            else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                EditExerciseAdapter.ViewHolder vh = (EditExerciseAdapter.ViewHolder)viewHolder;
                vh.viewBackground.setVisibility(View.GONE);
                vh.viewForeground.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final View foregroundView = ((EditExerciseAdapter.ViewHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
        else {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        EditExerciseAdapter.ViewHolder vh = (EditExerciseAdapter.ViewHolder)viewHolder;
        vh.viewForeground.setBackgroundColor(Color.WHITE);
        getDefaultUIUtil().clearView(vh.viewForeground);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final View foregroundView = ((EditExerciseAdapter.ViewHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
        else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onItemSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public interface ExerciseTouchListener {
        void onItemSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
        void onItemMove(int fromPosition, int toPosition);
    }

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }
}