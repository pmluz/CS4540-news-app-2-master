package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;

// it invokes the the notificationUtils
public class UpdateTask {
    public static final String DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String UPDATE_NEWS = "update_news";

    public static void executeUpdateTask(Context context, String action) {
        // only runs when jobservice runs
        if (UPDATE_NEWS.equals(action)) {
            NotificationUtils.newsNotification(context); // notifies the user
            Log.d("UpdateTask", "executeTask: ");
        }
        // cancels all the notifications
        else if(DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        }
    }

}
