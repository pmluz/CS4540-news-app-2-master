package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

// hw3: AutoSync part
// creates the jobdispatcher and dispatches it
public class UpdateUtils {
    private static final int AUTOSYNC_IN_SECONDS = 10;
    private static final String UPDATE_JOB_TAG = "news_update_tag";
    private static boolean isInitialized;

    synchronized public static void scheduleUpdate(Context context) {
        if(isInitialized) {
            return;
        }

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);

        Job jobReminder = jobDispatcher.newJobBuilder()
                .setService(FirebaseJobService.class)
                .setTag(UPDATE_JOB_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                // recurring and trigger means that it is periodic since it is true
                // trigger means the code will run the job from 10 to 20 seconds
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(AUTOSYNC_IN_SECONDS,
                        AUTOSYNC_IN_SECONDS + AUTOSYNC_IN_SECONDS))
                // means that if it is true & there's a new notif,
                // then old notif disappears & new one will show up
                .setReplaceCurrent(true)
                .build();
        jobDispatcher.schedule(jobReminder);
        isInitialized = true;
        Log.d("UpdateUtils", "scheduleUpdate(): ");
    }
}
