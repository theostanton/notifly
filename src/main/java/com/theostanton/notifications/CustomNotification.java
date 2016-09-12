package com.theostanton.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 12/09/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomNotification extends BaseNotification {

    private Notification.Builder builder;

    private CustomNotification(Context context, RemoteViews contentView, @DrawableRes int smallIconRes) {
        this.context = context;
        builder = new Notification.Builder(context)
                .setSmallIcon(smallIconRes)
                .setCustomContentView(contentView)
                .setStyle(new Notification.DecoratedCustomViewStyle());
    }

    public static CustomNotification create(@NonNull Context context, @NonNull RemoteViews contentView, @DrawableRes int smallIconRes) {
        return new CustomNotification(context,contentView,smallIconRes);
    }

    public CustomNotification setLargeIcon(Icon largeIcon){
        builder.setLargeIcon(largeIcon);
        return this;
    }

    public CustomNotification setLargeIcon(Bitmap largeIcon){
        builder.setLargeIcon(largeIcon);
        return this;
    }

    public int show() {
        int notificationId = updateCurrent ? DEFAULT_ID : new Random().nextInt();
        log("show() sent=%s", sent);

        if (sent) {
            Log.e(TAG, "Notification already shown");
            return -1;
        }

        Notification notificationCompat = builder.build();
        notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationCompat);

        return notificationId;
    }

}
