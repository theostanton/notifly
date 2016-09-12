package com.theostanton.notifications;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.theostanton.notifications.actions.NotificationAction;
import com.theostanton.notifications.actions.NotificationReply;
import com.theostanton.notifications.styles.NotificationStyle;

import java.util.HashMap;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 12/09/2016.
 */
public class Notify extends BaseNotification {

    private static final String TAG = "Notify";

    private NotificationCompat.Builder builder;
    @Nullable private NotificationIntent notificationIntent;
    @Nullable private NotificationStyle notificationStyle;
    @Nullable private NotificationReply notificationReply;
    @Nullable private HashMap<String, NotificationAction> actions;

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


    // Current notifications

    public Notify replaceDefault() {
        updateCurrent = true;
        return this;
    }


    public Notify append() {
        updateCurrent = false;
        return this;
    }

    // Expanded

    public Notify style(NotificationStyle notificationStyle) {
        this.notificationStyle = notificationStyle;
        return this;
    }

    // Actions

    public Notify addAction(NotificationAction notificationAction) {
        if (actions == null) actions = new HashMap<>();
        if (actions.containsKey(notificationAction.getLabel())) {
            Log.e(TAG, "Action with label already exists");
        }
        actions.put(notificationAction.getLabel(), notificationAction);
        return this;
    }

    // Custom

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static CustomNotification createCustom(@NonNull Context context, @NonNull RemoteViews contentView,@DrawableRes int smallIconRes){
        return CustomNotification.create(context,contentView,smallIconRes);
    }

    // Reply

    public Notify reply(NotificationReply notificationReply) {
        this.notificationReply = notificationReply;
        return this;
    }


    public int show() {
        int notificationId = updateCurrent ? DEFAULT_ID : new Random().nextInt();
        log("show() sent=%s", sent);

        if (sent) {
            Log.e(TAG, "Notification already shown");
            return -1;
        }
        sent = true;

        if (notificationReply != null) {
            builder.addAction(notificationReply.get(context, notificationId));
        }

        if (notificationStyle != null) {
            builder.setStyle(notificationStyle.get());
        }

        if (notificationIntent != null) {
            builder.setContentIntent(notificationIntent.getPendingIntent(context, notificationId));
        }

        Notification notificationCompat = builder.build();
        notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationCompat);


        return notificationId;
    }

    public static void clear(Context conext, int notificationId) {
        NotificationManager manager = (NotificationManager) conext.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

    public static void onLaunch(Activity activity) {
        NotificationManager manager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(activity.getIntent().getIntExtra(ID_ARG, -1));
    }
}
