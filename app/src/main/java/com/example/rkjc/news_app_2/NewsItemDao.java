package com.example.rkjc.news_app_2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

// Data Access Objects (DAO) enables to access the app's data
// Reference from the Lecture Slide
@Dao
public interface NewsItemDao {
    @Insert
    void insert(List<NewsItem> newsItemList);

    //If conflicts are possible, you can use @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Delete
    void delete(List<NewsItem> newsItemList);

    @Query("DELETE FROM news_item")
    void deleteAll();

    @Query("SELECT * FROM news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> getAllNewsItems();
}
