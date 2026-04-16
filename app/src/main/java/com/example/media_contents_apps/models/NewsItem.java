// models/NewsItem.java
package com.example.media_contents_apps.models;

import java.io.Serializable;

public class NewsItem implements Serializable {
    private final int id;
    private final String title;
    private final String source;
    private final String description;
    private final String category;
    private final boolean featured;
    private final int imageRes;

    public NewsItem(int id, String title, String source, String description, String category, boolean featured, int imageRes) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.description = description;
        this.category = category;
        this.featured = featured;
        this.imageRes = imageRes;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isFeatured() {
        return featured;
    }

    public int getImageRes() {
        return imageRes;
    }
}