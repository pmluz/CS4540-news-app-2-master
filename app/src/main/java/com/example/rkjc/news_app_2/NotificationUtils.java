package com.example.rkjc.news_app_2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

// handles all Notif related Actions/Settings
public class NotificationUtils {
    private static final int PENDING_INTENT_ID = 1;
    private static final int NOTIFICATION_ID = 2; //
    private static final int IGNORE_NOTIFICATION_PENDING_INTENT = 10;
    private static final String NOTIFICATION_CHANNEL_ID = "update_notification_channel";

    // called in UpdateTask when the jobservice runs
    public static void newsNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // creates the NotificationChannel, but only on API 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Primary", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // builder
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications) // displays in the notif
                .setLargeIcon(largeIcon(context)) // displays in the notif, but calls largeIcon()
                .setContentTitle("News Update")
                .setContentText("There are new news")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("There are new news"))
                // set the intent when the user taps the notif
                .setContentIntent(contentIntent(context))
                .addAction(ignoreNotificationAction(context))
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(true);

        // this makes it compatible to API 26 and lower
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH); // means it pushes the notif to the user
        }
        // this will show the notification
        // each notification int is unique for each notif that must be defined
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    // Needed Bitmap to set the largeIcon
    private static Bitmap largeIcon(Context context) {
        Resources resources = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_notifications);
        return largeIcon;
    }

    // PendingIntent means granting another application the right to perform the specified operation
    private static PendingIntent contentIntent(Context context) {
        Intent activityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, PENDING_INTENT_ID,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // It handles the action when the user wants to ignore the notification
    public static NotificationCompat.Action ignoreNotificationAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, NewsIntentService.class);
        ignoreReminderIntent.setAction(UpdateTask.DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent =
                PendingIntent.getService(context, IGNORE_NOTIFICATION_PENDING_INTENT,
                        ignoreReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction =
                new NotificationCompat.Action(
                        R.drawable.ic_cancel_black_24dp,
                        "Dismiss",
                        ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    // Clears all the notifs
    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
