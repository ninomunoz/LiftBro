package com.example.liftbro.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import static com.example.liftbro.data.LiftContract.WorkoutEntry;

/**
 * Created by i57198 on 10/25/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mCursor;
    private Context mContext;
    int mWidgetId;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }

        final long token = Binder.clearCallingIdentity();
        try {
            mCursor = mContext.getContentResolver().query(
                    WorkoutEntry.CONTENT_URI,
                    null, null, null, WorkoutEntry.COLUMN_NAME
            );
        }
        finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);

        if (mCursor.moveToPosition(position)) {
            String workoutName = mCursor.getString(mCursor.getColumnIndex(WorkoutEntry.COLUMN_NAME));
            remoteViews.setTextViewText(android.R.id.text1, workoutName);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (mCursor.moveToPosition(position)) {
            return mCursor.getLong(mCursor.getColumnIndex(WorkoutEntry._ID));
        }
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}