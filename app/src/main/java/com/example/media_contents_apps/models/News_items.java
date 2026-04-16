package com.example.media_contents_apps.models;

import java.io.Serializable;

// This model class is used to store
// all information for one news item.
public class News_items implements Serializable {

    // Unique id for each news item.
    private final int id;

    // Title of the news.
    private final String title;

    // News source name.
    private final String source;

    // Full description of the news.
    private final String description;

    // Category such as Football, Basketball, Cricket.
    private final String category;

    // This value checks whether the item
    // should appear in featured section.
    private final boolean featured;

    // This stores drawable image resource id
    // for thumbnail picture.
    private final int imageRes;

    // Constructor is used to assign values
    // when a new object is created.
    public News_items(
            int id,
            String title,
            String source,
            String description,
            String category,
            boolean featured,
            int imageRes
    ) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.description = description;
        this.category = category;
        this.featured = featured;
        this.imageRes = imageRes;
    }

    // This method returns id.
    public int getId() {
        return id;
    }

    // This method returns title.
    public String getTitle() {
        return title;
    }

    // This method returns source name.
    public String getSource() {
        return source;
    }

    // This method returns description.
    public String getDescription() {
        return description;
    }

    // This method returns category name.
    public String getCategory() {
        return category;
    }

    // This method checks whether item is featured.
    public boolean isFeatured() {
        return featured;
    }

    // This method returns image resource id.
    // This is used to show thumbnail picture.
    public int getImageRes() {
        return imageRes;
    }
}