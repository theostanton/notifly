package com.theostanton.notifications;

import android.content.Context;
import android.util.Log;

/**
 * Created by theostanton on 19/09/2016.
 */

abstract class NotificationCreator {

    protected static final String TAG = "NotificationCreator";

    protected boolean sent = false;

    protected boolean updateCurrent = true;

    protected Context context;

    public abstract int show();

    protected static void log(String text, Object... args) {
        if (Notifly.log) {
            Log.d(TAG, String.format(text, args));
        }
    }

    protected static void error(String text, Object... args) {
        Log.e(TAG, String.format(text, args));
    }

    protected static void exception(String text, Throwable throwable, Object... args) {
        Log.e(TAG, String.format(text, args), throwable);
    }

}
