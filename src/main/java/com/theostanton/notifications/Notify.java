package com.theostanton.notifications;

import android.util.Log;

/**
 * Created by theostanton on 12/09/2016.
 */
public class Notify {

    private static final boolean log = true;
    private static final String TAG = "Notify";

    private Notify() {}

    private static void log(String text, Object... args){
        if(log){
            Log.d(TAG,String.format(text,args));
        }
    }

    public static void simple(String text){
        log("simple text=`%s`",text);
    }
}
