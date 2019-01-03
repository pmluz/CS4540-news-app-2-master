package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    /* static class members: parsing the url */
    // https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a0e4c74e78f94bfc9feb5dbe20a0ad30
    final static String base_url = "https://newsapi.org/v1/articles";
    final static String sourceWord = "source";
    final static String theNextWebWord = "the-next-web";
    final static String sortByWord = "sortBy";
    final static String latestWord = "latest";
    final static String apiKeyWord = "apiKey";
    final static String apiKey = "a0e4c74e78f94bfc9feb5dbe20a0ad30";

    /* Method that uses Uri.Builder to build appropriate url,
     * same as the one from above
     * Returns a Java URL object
     */
    public static URL buildURL() {
        Uri buildURI = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(sourceWord, theNextWebWord)
                .appendQueryParameter(sortByWord, latestWord)
                .appendQueryParameter(apiKeyWord, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(buildURI.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // api call
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return  scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
