package com.example.liftbro.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by i57198 on 10/25/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this.getApplicationContext(), intent);
    }
}
