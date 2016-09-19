package com.theostanton.notifications.styles;

import android.support.v4.app.NotificationCompat;

/**
 * Created by theostanton on 19/09/2016.
 */

public class BigTextStyle extends NotificationStyle {

    private String title;
    private String text;

    public BigTextStyle(String title, String text){
        this.title = title;
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public NotificationCompat.Style get() {
        return new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(text);
    }

}
