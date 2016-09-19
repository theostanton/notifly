package com.theostanton.notifications.actions;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.RemoteInput;

import com.theostanton.notifications.NotificationIntent;


/**
 * Created by theostanton on 12/09/2016.
 */

public class NotificationReply {

    private String replyTextKey;
    private String replyLabel;
    private @DrawableRes int replyIcon;
    private Class<? extends Activity> activityClass;

    public NotificationReply(String replyTextKey, String replyLabel, @DrawableRes int replyIcon, Class<? extends Activity> activityClass) {
        this.replyTextKey = replyTextKey;
        this.replyLabel = replyLabel;
        this.replyIcon = replyIcon;
        this.activityClass = activityClass;
    }

    public Action get(Context context, int notificationId) {

        RemoteInput remoteInput = new RemoteInput.Builder(replyTextKey)
                .setLabel(replyLabel)
                .build();


        return new Action.Builder(replyIcon, replyLabel,
                    NotificationIntent.activity(activityClass).getPendingIntent(context,notificationId)
                )
                .addRemoteInput(remoteInput)
                .build();

    }

    @Override
    public String toString() {
        return "NotificationReply{" +
                "replyTextKey='" + replyTextKey + '\'' +
                ", replyLabel='" + replyLabel + '\'' +
                ", replyIcon=" + replyIcon +
                ", activityClass=" + activityClass +
                '}';
    }
}
