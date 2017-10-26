package com.example.liftbro.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.liftbro.R;
import com.example.liftbro.activity.MainActivity;

/**
 * Created by i57198 on 10/25/17.
 */

public class LiftWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.lift_widget);

            // Attach adapter to view
            Intent adapter = new Intent(context, WidgetService.class);
            adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setRemoteAdapter(R.id.widget_workout_list, adapter);

            // Pending intent template for collection items
            Intent workoutDetailIntent = new Intent(context, MainActivity.class);
            PendingIntent workoutDetailPendingIntent = PendingIntent.getActivity(context, 0, workoutDetailIntent, 0);
            views.setPendingIntentTemplate(R.id.widget_workout_list, workoutDetailPendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_workout_list);
        }
    }
}