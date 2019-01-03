package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;

// *pending intent needs IntentService for it to handle -> overriding the method below
public class NewsIntentService extends IntentService {
    //    String name = "NewsIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        String action = intent.getAction();
        UpdateTask.executeUpdateTask(this, action);
    }
}

