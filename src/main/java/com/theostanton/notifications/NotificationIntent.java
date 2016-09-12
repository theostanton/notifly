package com.theostanton.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by theostanton on 12/09/2016.
 */

public class NotificationIntent {

    private Class<? extends Activity> activityClass;
    private Map<String, Object> arguments;

    private NotificationIntent(Class<? extends Activity> activityClass) {
        this.activityClass = activityClass;
    }

    public static NotificationIntent create(Class<? extends Activity> activityClass){
        return new NotificationIntent(activityClass);
    }

    public NotificationIntent withArgument(String key, Object value) {
        if (arguments == null) arguments = new HashMap<>();
        arguments.put(key, value);
        return this;
    }

    public PendingIntent getPendingIntent(Context context, int notificationId) {
        withArgument(Notify.ID_ARG,notificationId);
        return getPendingIntent(context);

    }
    public PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, activityClass);
        if (arguments != null) {
            for (Map.Entry<String, Object> entry : arguments.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof String[]) {
                    intent.putExtra(key, (String[]) value);
                } else if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) value);
                } else if (value instanceof Integer[]) {
                    intent.putExtra(key, (Integer[]) value);
                } else if (value instanceof Float) {
                    intent.putExtra(key, (Float) value);
                } else if (value instanceof Float[]) {
                    intent.putExtra(key, (Float[]) value);
                }
            }
        }

        return PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

}
