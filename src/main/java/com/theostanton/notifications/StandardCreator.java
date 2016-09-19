package com.theostanton.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.theostanton.notifications.actions.NotificationAction;
import com.theostanton.notifications.actions.NotificationReply;
import com.theostanton.notifications.styles.BigTextStyle;
import com.theostanton.notifications.styles.ExpandedNotification;
import com.theostanton.notifications.styles.NotificationStyle;

import java.util.HashMap;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 19/09/2016.
 */

public class StandardCreator extends NotificationCreator {

    protected static final String TAG = "StandardCreator";

    // Required
    private final Context context;
    private final String contentTitle;
    private final String contentText;
    private final @DrawableRes int smallIconRes;

    // Optional
    @Nullable private NotificationIntent notificationIntent;
    @Nullable private NotificationStyle notificationStyle;
    @Nullable private NotificationReply notificationReply;
    @Nullable private HashMap<String, NotificationAction> actions;

    public StandardCreator(Context context, String contentTitle, String contentText, int smallIconRes) {
        this.context = context;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.smallIconRes = smallIconRes;
    }

    // Actions

    public StandardCreator addAction(NotificationAction notificationAction) {
        if (actions == null) actions = new HashMap<>();
        if (actions.containsKey(notificationAction.getLabel())) {
            Log.e(TAG, "Action with label already exists");
        }
        actions.put(notificationAction.getLabel(), notificationAction);
        return this;
    }


    public StandardCreator addAction(@NonNull String label, @DrawableRes int iconRes, @NonNull NotificationIntent notifcationIntent) {
        addAction(NotificationAction.create(label, iconRes, notifcationIntent));
        return this;
    }

    // Reply
    @RequiresApi(api = Build.VERSION_CODES.N)
    public StandardCreator reply(NotificationReply notificationReply) {
        log("add reply : %s", notificationReply);
        this.notificationReply = notificationReply;
        return this;
    }

    private boolean checkHasStyle() {
        if (notificationStyle == null) {
            log("checkHasStyle notificationStyle==null");
            return false;
        }
        if (notificationStyle instanceof ExpandedNotification) {
            error("Already expandable");
        }
        return true;
    }

    // Expanded

    public StandardCreator expandable() {
        return expandable(contentTitle, contentText);
    }

    public StandardCreator expandable(@Nullable String title, @Nullable String text) {
        if (checkHasStyle()) {
            return this;
        }
        notificationStyle = new BigTextStyle(
                title == null ? contentTitle : title,
                text == null ? contentText : text
        );
        return this;
    }

    public StandardCreator expandedTitle(String title) {
        if (notificationStyle == null) {
            expandable(title, contentText);
        } else if (notificationStyle instanceof BigTextStyle) {
            ((BigTextStyle) notificationStyle).setTitle(title);
        } else {
            checkHasStyle();
        }
        return this;
    }

    public StandardCreator expandedText(String text) {
        if (notificationStyle == null) {
            expandable(contentTitle, text);
        } else if (notificationStyle instanceof BigTextStyle) {
            ((BigTextStyle) notificationStyle).setText(text);
        } else {
            checkHasStyle();
        }
        return this;
    }

    public StandardCreator setExpandedText(String expandedText) {
        if (notificationStyle == null) {
            expandable();
        } else if (notificationStyle instanceof ExpandedNotification) {
            notificationStyle = ExpandedNotification.create(expandedText).title(contentTitle);
        } else {
            checkHasStyle();
        }
        return this;
    }

    public int show() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentText(contentText)
                .setContentTitle(contentTitle)
                .setSmallIcon(smallIconRes);

        int notificationId = updateCurrent ? Notifly.DEFAULT_ID : new Random().nextInt();
        log("show() sent=%s", sent);

        if (sent) {
            Log.e(TAG, "Notification already shown");
            return -1;
        }
        sent = true;

        if (notificationReply != null) {
            log("Set notificationReply to %s", notificationReply);
            builder.addAction(notificationReply.get(context, notificationId));
        }

        if (actions != null) {
            for (NotificationAction action : actions.values()) {
                builder.addAction(action.get(context, notificationId));
            }
        }

        if (notificationStyle != null) {
            log("Set notificationStyle to %s", notificationStyle);
            builder.setStyle(notificationStyle.get());
        }

        if (notificationIntent != null) {
            log("Set notificationIntent to %s", notificationIntent);
            builder.setContentIntent(notificationIntent.getPendingIntent(context, notificationId));
        }

        Notification notificationCompat = builder.build();
        notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationCompat);


        return notificationId;
    }


}
