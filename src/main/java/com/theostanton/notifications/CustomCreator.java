package com.theostanton.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by theostanton on 19/09/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class CustomCreator extends NotificationCreator {

    private final Context context;
    private final RemoteViews contentView;
    private final @DrawableRes int smallIconRes;
    private @Nullable Icon largeIconIcon;
    private @Nullable Bitmap largeIconBitmap;

    public CustomCreator(Context context, RemoteViews contentView, @DrawableRes int smallIconRes) {
        this.context = context;
        this.contentView = contentView;
        this.smallIconRes = smallIconRes;
    }

    public CustomCreator setLargeIcon(Icon largeIcon){
        largeIconIcon = largeIcon;
        return this;
    }

    public CustomCreator setLargeIcon(Bitmap largeIcon){
        largeIconBitmap = largeIcon;
        return this;
    }

    @Override
    public int show(){

        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(smallIconRes)
                .setCustomContentView(contentView)
                .setStyle(new Notification.DecoratedCustomViewStyle());

        int notificationId = updateCurrent ? Notifly.DEFAULT_ID : new Random().nextInt();
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
