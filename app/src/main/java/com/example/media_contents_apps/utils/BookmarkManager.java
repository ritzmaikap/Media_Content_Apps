package com.example.media_contents_apps.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.NewsItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookmarkManager {

    private static final String PREF_NAME = "sports_news_prefs";
    private static final String KEY_BOOKMARKS = "bookmarks";

    public static void toggleBookmark(Context context, int newsId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> current = new HashSet<>(preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>()));

        String id = String.valueOf(newsId);
        if (current.contains(id)) {
            current.remove(id);
        } else {
            current.add(id);
        }

        preferences.edit().putStringSet(KEY_BOOKMARKS, current).apply();
    }

    public static boolean isBookmarked(Context context, int newsId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> current = preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>());
        return current.contains(String.valueOf(newsId));
    }

    public static List<NewsItem> getBookmarkedNews(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> ids = preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>());
        List<NewsItem> items = new ArrayList<>();

        for (String id : ids) {
            NewsItem item = DummyData.getNewsById(Integer.parseInt(id));
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }
}