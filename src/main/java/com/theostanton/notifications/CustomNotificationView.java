package com.theostanton.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

/**
 * Created by theostanton on 12/09/2016.
 */

public class CustomNotificationView extends RemoteViews {

    private final Context context;


    private CustomNotificationView(Context context, @LayoutRes int layoutId) {
        super(context.getPackageName(), layoutId);
        this.context = context;
    }

    private CustomNotificationView addAction(@NonNull Context context, @IdRes int viewId, @NonNull String action, int requestCode) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    public static CustomNotificationView from(@NonNull Context context, @LayoutRes int layoutId) {
        return new CustomNotificationView(context, layoutId);
    }

    public CustomNotificationView addAction(@IdRes int viewId, @NonNull String action) {
        return addAction(viewId, action, 111);
    }

    public CustomNotificationView addAction(@IdRes int viewId, @NonNull String action, int requestCode) {
        addAction(context, viewId, action, requestCode);
        return this;
    }

}
