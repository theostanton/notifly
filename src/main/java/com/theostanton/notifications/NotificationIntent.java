package com.theostanton.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by theostanton on 12/09/2016.
 */

public class NotificationIntent {

    @Nullable private Class<? extends Activity> activityClass;
    @Nullable private Class<? extends Service> serviceClass;
    @Nullable private Map<String, Object> arguments;

    private NotificationIntent(@Nullable Class<? extends Service> serviceClass,@Nullable Class<? extends Activity> activityClass) {
        this.serviceClass = serviceClass;
        this.activityClass = activityClass;
    }

    public static NotificationIntent activity(Class<? extends Activity> activityClass){
        return new NotificationIntent(null,activityClass);
    }

    public static NotificationIntent service(Class<? extends Service> serviceClass){
        return new NotificationIntent(serviceClass,null);
    }

    public NotificationIntent withArgument(String key, Object value) {
        if (arguments == null) arguments = new HashMap<>();
        arguments.put(key, value);
        return this;
    }

    public PendingIntent getPendingIntent(Context context, int notificationId) {
        withArgument(Notifly.ID_ARG,notificationId);
        return getPendingIntent(context);

    }
    public PendingIntent getPendingIntent(Context context) {

        Intent intent;

        if(activityClass!=null){
            intent = new Intent(context, activityClass);
        } else if(serviceClass !=null){
            intent = new Intent(context,serviceClass);
        } else {
            Log.w(TAG,"No serviceClass or activityClass");
            throw new RuntimeException("No serviceClass or activityClass given");
        }

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

        if(activityClass!=null) {
            return PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        else{
            return PendingIntent.getService(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

    }

}
