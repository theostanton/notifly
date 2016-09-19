package com.theostanton.notifications.styles;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by theostanton on 12/09/2016.
 */

public class ExpandedNotification extends NotificationStyle {

    private static final String TAG = "ExpandedNotification";

    private String expandedTitle;
    private String[] expandedLines;
    private String summaryText;

    private ExpandedNotification(String[] lines) {
        expandedLines = lines;
    }

    private ExpandedNotification(String summaryText) {
        this.summaryText = summaryText;
    }

    public static ExpandedNotification create(String... lines) {
        return new ExpandedNotification(lines);
    }

    public static ExpandedNotification create(String summaryText) {
        return new ExpandedNotification(summaryText);
    }

    public ExpandedNotification title(String expandedTitle){
        this.expandedTitle = expandedTitle;
        return this;
    }


    @Override
    public NotificationCompat.Style get() {
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        if (expandedTitle != null) {
            inboxStyle.setBigContentTitle(expandedTitle);
        } else {
            Log.e(TAG, "No expandedTitle set");
        }

        if (expandedLines != null) {
            for (String line : expandedLines) {
                inboxStyle.addLine(line);
            }
        } else if (summaryText != null) {
            inboxStyle.setSummaryText(summaryText);
        } else {
            Log.e(TAG, "No content text");
        }
        return inboxStyle;


    }

    @Override
    public String toString() {
        return "ExpandedNotification{" +
                "expandedTitle='" + expandedTitle + '\'' +
                ", expandedLines=" + Arrays.toString(expandedLines) +
                ", summaryText='" + summaryText + '\'' +
                "} " + super.toString();
    }
}
