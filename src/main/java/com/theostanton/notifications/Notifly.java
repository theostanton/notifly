package com.theostanton.notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 19/09/2016.
 */

public class Notifly {

    public static final int DEFAULT_ID = 1066;
    public static final String ID_ARG = "notify_id_arg";
    protected static boolean log = true;

    public static void setLogging(boolean logging){
        log = logging;
    }

    // Standard

    public static StandardCreator create(@NonNull Context context, @NonNull String contentTitle, @NonNull String contentText, @DrawableRes int smallIconRes) {
        return new StandardCreator(context, contentTitle, contentText, smallIconRes);
    }

    public static StandardCreator create(@NonNull Context context, @NonNull String contentText, @DrawableRes int smallIconRes) {
        return new StandardCreator(context, Helpers.getAppLabel(context), contentText, smallIconRes);
    }

    public static StandardCreator create(@NonNull Context context, @NonNull String contentTitle, @NonNull String contentText) {
        return create(context, contentTitle, contentText, Helpers.getAppIconRes(context));
    }

    public static StandardCreator create(@NonNull Context context, @NonNull String contentText) {
        return create(context, Helpers.getAppLabel(context), contentText, Helpers.getAppIconRes(context));
    }

    // Custom

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CustomCreator create(@NonNull Context context, RemoteViews contentView, @DrawableRes int smallIconRes) {
        return new CustomCreator(context, contentView, smallIconRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CustomCreator create(@NonNull Context context, RemoteViews contentView) {
        return new CustomCreator(context, contentView, Helpers.getAppIconRes(context));
    }


    // Utils

    public static void clear(Context conext, int notificationId) {
        NotificationManager manager = (NotificationManager) conext.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

    public static void onLaunch(Activity activity) {
        NotificationManager manager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(activity.getIntent().getIntExtra(ID_ARG, -1));
    }

}