package com.theostanton.notifications;

import android.content.Context;
import android.util.Log;

/**
 * Created by theostanton on 12/09/2016.
 */

abstract class BaseNotification<T> {

    protected static final String TAG = "BaseNotification";

    public static final int DEFAULT_ID = 1066;
    public static final String ID_ARG = "notify_id_arg";
    protected static final boolean log = true;

    protected boolean sent = false;

    protected boolean updateCurrent = true;

    protected Context context;

    public abstract int show();

    protected static void log(String text, Object... args) {
        if (log) {
            Log.d(TAG, String.format(text, args));
        }
    }

    private static void error(String text, Object... args) {
        Log.e(TAG, String.format(text, args));
    }

    private static void error(String text, Throwable throwable, Object... args) {
        Log.e(TAG, String.format(text, args), throwable);
    }
}
