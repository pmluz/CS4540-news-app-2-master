package com.example.rkjc.news_app_2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    /* Method that will parse the JSON you received into an ArrayList<NewsItem>. */
    public static ArrayList<NewsItem> parseNews(String JSONString) {
        ArrayList<NewsItem> itemList = new ArrayList<>();
        try {
            JSONObject main = new JSONObject(JSONString);
            JSONArray list = main.getJSONArray("articles");

            for(int i = 0; i < list.length(); i++) {
                JSONObject item = list.getJSONObject(i);
                itemList.add(new NewsItem(
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("url"),
                        item.getString("publishedAt"),
                        item.getString("author"),
                        item.getString("urlToImage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

}


