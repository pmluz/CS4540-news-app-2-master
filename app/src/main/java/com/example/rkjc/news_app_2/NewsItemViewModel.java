package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;

// Middleman that connects everything from the background to the UI
// it is responsible for preparing and managing data for an Activity or a Fragment
// also handles communication with the rest of the app
// purpose is to get and keep info that is necessary for the Activity or Fragment
public class NewsItemViewModel extends AndroidViewModel {
    private NewsItemRepository newsItemRepository;
    private LiveData<List<NewsItem>> newsItemList;

    // constructor
    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        newsItemRepository = new NewsItemRepository(application);
        newsItemList = newsItemRepository.getAllWords();
    }

    // returns the LiveData list
    public LiveData<List<NewsItem>> getNewsItemList() {
        return newsItemList;
    }

    // calls newSearchQuery() which executes when Refresh is clicked
    public void update() {
        newsItemRepository.newsSearchQuery();
    }
}
