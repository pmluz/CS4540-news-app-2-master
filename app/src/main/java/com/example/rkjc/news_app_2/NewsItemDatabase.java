package com.example.rkjc.news_app_2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// saves data in the local database that enables the user to access it
@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsItemDatabase extends RoomDatabase {
    public abstract  NewsItemDao newsItemDao();
    private static volatile NewsItemDatabase newsItemDatabase;

    static NewsItemDatabase getDatabase(Context context) {
        if(newsItemDatabase == null) {
            synchronized (NewsItemDatabase.class) {
                if(newsItemDatabase == null) {
                    newsItemDatabase = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NewsItemDatabase.class,
                            "newsItemDatabase")
                            .build();
                }
            }
        }
        return newsItemDatabase;
    }

}
