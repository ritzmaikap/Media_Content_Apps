package com.example.media_contents_apps.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.News_items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class is used to manage bookmarks
// using SharedPreferences.
public class Manage_Bookmark {

    // This is the SharedPreferences file name.
    private static final String PREF_NAME = "sports_news_prefs";

    // This key stores bookmarked ids.
    private static final String KEY_BOOKMARKS = "bookmarks";

    // This method adds or removes bookmark
    // depending on current state.
    public static void toggleBookmark(Context context, int newsId) {

        // I open SharedPreferences file.
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // I get saved bookmark ids.
        Set<String> current =
                new HashSet<>(preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>()));

        // I convert integer id into String.
        String id = String.valueOf(newsId);

        // If already bookmarked, I remove it.
        if (current.contains(id)) {
            current.remove(id);
        } else {

            // Otherwise, I add it.
            current.add(id);
        }

        // I save updated bookmark list.
        preferences.edit()
                .putStringSet(KEY_BOOKMARKS, current)
                .apply();
    }

    // This method checks whether
    // a news item is bookmarked.
    public static boolean isBookmarked(Context context, int newsId) {

        // I open SharedPreferences.
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // I get current saved ids.
        Set<String> current =
                preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>());

        // I return true if id exists.
        return current.contains(String.valueOf(newsId));
    }

    // This method returns full bookmarked news items.
    public static List<News_items> getBookmarkedNews(Context context) {

        // I open SharedPreferences.
        SharedPreferences preferences =
                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // I get all bookmarked ids.
        Set<String> ids =
                preferences.getStringSet(KEY_BOOKMARKS, new HashSet<>());

        // I create empty list for results.
        List<News_items> items = new ArrayList<>();

        // I check each saved id.
        for (String id : ids) {

            // I find matching news item from dummy data.
            News_items item =
                    DummyData.getNewsById(Integer.parseInt(id));

            // If item exists, I add it to list.
            if (item != null) {
                items.add(item);
            }
        }

        // I return all bookmarked news items.
        return items;
    }
}