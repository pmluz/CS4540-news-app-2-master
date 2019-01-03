package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.URL;
import java.util.List;

// stores all the info about the articles
// it allows to insert and delete
public class NewsItemRepository {
    private NewsItemDao newsItemDao;
    private LiveData<List<NewsItem>> newsItemList;
    List<NewsItem> articles;

    // constructor
    public NewsItemRepository(Application application) {
        NewsItemDatabase dtbs = NewsItemDatabase.getDatabase(application.getApplicationContext());
        newsItemDao = dtbs.newsItemDao();
        newsItemList = newsItemDao.getAllNewsItems();
    }

    // moved from the MainActivity
    // executes refresh by itself
    public void newsSearchQuery() {
        URL url = NetworkUtils.buildURL();
        new NewsQueryTask().execute(url);
    }

    // it is called in the NewsItemRepository
    public LiveData<List<NewsItem>> getAllWords() {
        return newsItemList;
    }

    public void insert(List<NewsItem> itemList) {
        new insertAsyncTask(newsItemDao).execute(itemList);
    }

    // keeps adding
    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mNewsItemDao;
        insertAsyncTask(NewsItemDao dao) {
            mNewsItemDao = dao;
        }

        @Override
        protected Void doInBackground(List<NewsItem>... lists) {
            for(int i = 0; i < lists.length; i++) {
                mNewsItemDao.insert(lists[0]);
            }
            return null;
        }
    }

    public void delete() {
        new deleteAsyncTask(newsItemDao).execute();
    }

    private class deleteAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mNewsItemDao;
        deleteAsyncTask(NewsItemDao dao) {
            mNewsItemDao = dao;
        }

        @Override
        protected Void doInBackground(List<NewsItem>... lists) {
            mNewsItemDao.deleteAll();
            return null;
        }
    }

    // moved from MainActivity
    class NewsQueryTask extends  AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
           URL url = urls[0];
           String results = null;
           try {
                results = NetworkUtils.getResponseFromHttpUrl(url);
           } catch(IOException e) {
               e.printStackTrace();
           }
            return results;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete(); // clears all current entries in the database
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            articles = JsonUtils.parseNews(s);
            insert(articles);
        }
    }
}
