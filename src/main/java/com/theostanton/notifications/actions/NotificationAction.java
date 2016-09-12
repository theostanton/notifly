package com.theostanton.notifications.actions;

/**
 * Created by theostanton on 12/09/2016.
 */

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.theostanton.notifications.NotificationIntent;

public class NotificationAction {

    private String label;
    private NotificationIntent notificationIntent;
    private @DrawableRes int iconRes;

    private NotificationAction(@NonNull String label, @DrawableRes int iconRes, @NonNull NotificationIntent notificationIntent) {
        this.label = label;
        this.notificationIntent = notificationIntent;
        this.iconRes = iconRes;
    }

    public String getLabel(){
        return label;
    }

    public static NotificationAction create(@NonNull String label, @DrawableRes int iconRes, @NonNull NotificationIntent notifcationIntent){
        return new NotificationAction(label,iconRes,notifcationIntent);
    }

    public NotificationCompat.Action get(@NonNull Context context, int notifcationId){
        return new NotificationCompat.Action.Builder(
                iconRes,
                label,
                notificationIntent.getPendingIntent(context,notifcationId)
        ).build();
    }



}
