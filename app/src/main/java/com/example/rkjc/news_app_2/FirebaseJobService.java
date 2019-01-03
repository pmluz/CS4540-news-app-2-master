package com.example.rkjc.news_app_2;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


public class FirebaseJobService extends JobService {
    private AsyncTask asyncTask;
    NewsItemRepository newsItemRepository;

    // Returns true if the jobs needs to continue running
    // Returns false means the jobs is already finished and it will not call onStopJob
    @Override
    public boolean onStartJob(JobParameters job) {
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = FirebaseJobService.this;
                UpdateTask.executeUpdateTask(context, UpdateTask.UPDATE_NEWS);
                newsItemRepository = new NewsItemRepository(getApplication());
                newsItemRepository.newsSearchQuery();
                Log.d("ASYNCTASK", "onStartJob at FirebaseJobService");
                return null;
            }
        };
        asyncTask.execute();
        return false;
    }

    // This will called if req specified at sched time are no longer met
    // E.g. you have requested WIFI but the user suddenly turns it off
    @Override
    public boolean onStopJob(JobParameters job) {
        if(asyncTask != null) {
            asyncTask.cancel(true);
        }
        return false;
    }
}
