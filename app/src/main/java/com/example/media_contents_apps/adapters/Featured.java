package com.example.media_contents_apps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.models.News_items;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

// This adapter class is used to display featured news items
// inside a RecyclerView.
public class Featured extends RecyclerView.Adapter<Featured.FeaturedViewHolder> {

    // This interface is created to handle item click events.
    // When I click an item, the selected News_items object is passed.
    public interface OnFeaturedClickListener {
        void onFeaturedClick(News_items item);
    }

    // This list stores all featured news items.
    private final List<News_items> items;

    // This object listens for click events.
    private final OnFeaturedClickListener listener;

    // Constructor used to receive item list and click listener.
    public Featured(List<News_items> items, OnFeaturedClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Here, I connect the XML layout file with Java code.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.featured_items, parent, false);

        // I return a new ViewHolder object.
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        // I get the current item based on position.
        News_items item = items.get(position);

        // I set image from the model class.
        holder.image.setImageResource(item.getImageRes());

        // I set category text as title.
        holder.title.setText(item.getCategory());

        // When I click the item, selected data is sent.
        holder.itemView.setOnClickListener(v -> listener.onFeaturedClick(item));
    }

    @Override
    public int getItemCount() {

        // This returns total number of items in the list.
        return items.size();
    }

    // ViewHolder class is used to hold views for each row item.
    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        // ImageView for featured image.
        androidx.appcompat.widget.AppCompatImageView image;

        // TextView for title.
        MaterialTextView title;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            // I connect Java variables with XML views.
            image = itemView.findViewById(R.id.ivFeatured);
            title = itemView.findViewById(R.id.tvFeaturedTitle);
        }
    }
}