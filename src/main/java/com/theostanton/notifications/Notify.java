package com.theostanton.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.theostanton.notifications.styles.NotificationStyle;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 12/09/2016.
 */
public class Notify {

    private static final boolean log = true;
    private static final String TAG = "Notify";

    public static final int DEFAULT_ID = 1066;
    public static final String ID_ARG = "notify_id_arg";

    private boolean sent = false;
    private boolean updateCurrent = true;

    private NotificationCompat.Builder builder;
    private Context context;
    @Nullable private NotificationIntent notificationIntent;
    @Nullable private NotificationStyle notificationStyle;
    @Nullable private NotificationReply notificationReply;

    public Notify(Context context, @Nullable String contentTitle, String contentText, int smallIconRes) {

        builder = new NotificationCompat.Builder(context)
                .setContentText(contentText);

        if (smallIconRes != 0) {
            builder.setSmallIcon(smallIconRes);
        }

        if (contentTitle != null && contentTitle.length() > 0) {
            builder.setContentTitle(contentTitle);
        } else {
            builder.setContentTitle(Helpers.getAppLabel(context));
        }
        this.context = context;
    }

    // Create

    public static Notify create(Context context, @NonNull String contentTitle, @NonNull String contentText, @DrawableRes int smallIconRes) {
        return new Notify(context, contentTitle, contentText, smallIconRes);
    }

    public static Notify create(Context context, @NonNull String contentText, @DrawableRes int smallIconRes) {
        return new Notify(context, null, contentText, smallIconRes);

    }

    public static Notify create(Context context, @StringRes int contentTitleRes, @StringRes int contentTextRes, @DrawableRes int smallIconRes) {
        return new Notify(context, context.getString(contentTitleRes), context.getString(contentTextRes), smallIconRes);
    }

    public static Notify create(Context context, @StringRes int contentTextRes, @DrawableRes int smallIconRes) {
        return new Notify(context, null, context.getString(contentTextRes), smallIconRes);
    }

    // Current notifications

    public Notify replaceDefault() {
        updateCurrent = true;
        return this;
    }

    public Notify append() {
        updateCurrent = false;
        return this;
    }

    // Actions

    public Notify onClick(NotificationIntent notificationIntent) {
        this.notificationIntent = notificationIntent;
        return this;
    }

    public Notify onClick(Class<? extends Activity> activityClass) {
        Intent resultIntent = new Intent(context, activityClass);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        builder.setContentIntent(resultPendingIntent);
        return this;
    }

    // Expanded

    public Notify style(NotificationStyle notificationStyle) {
        this.notificationStyle = notificationStyle;
        return this;
    }

    // Reply

    public Notify reply(NotificationReply notificationReply){
        this.notificationReply = notificationReply;
        return this;
    }



    public int show() {
        int notificationId = updateCurrent ? DEFAULT_ID : new Random().nextInt();
        log("show() sent=%s", sent);

        if (sent) {
            throw new RuntimeException("Notification already shown");
        }

        if(notificationReply!=null){
            builder.addAction(notificationReply.get(context,notificationId));
        }

        if (notificationStyle != null) {
            builder.setStyle(notificationStyle.get());
        }

        if (notificationIntent != null) {
            builder.setContentIntent(notificationIntent.getPendingIntent(context,notificationId));
        }

        Notification notificationCompat = builder.build();
        notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationCompat);
        return notificationId;
    }

    private static void log(String text, Object... args) {

        if (log) {
            Log.d(TAG, String.format(text, args));
        }
    }

    public static void onLaunch(Activity activity){
        NotificationManager manager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(activity.getIntent().getIntExtra(ID_ARG, -1));
    }
}
