package com.example.media_contents_apps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.models.News_items;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

// This adapter class is used to display news items
// inside a RecyclerView.
public class News extends RecyclerView.Adapter<News.NewsViewHolder> {

    // This interface is used to handle click events
    // when I select a news item.
    public interface OnNewsClickListener {
        void onNewsClick(News_items item);
    }

    // This list stores all news data.
    private final List<News_items> items = new ArrayList<>();

    // This object listens for item click events.
    private final OnNewsClickListener listener;

    // Constructor used to receive data and click listener.
    public News(List<News_items> items, OnNewsClickListener listener) {
        this.items.addAll(items);
        this.listener = listener;
    }

    // This method updates the RecyclerView list
    // with new data.
    public void submitList(List<News_items> newItems) {
        items.clear();
        items.addAll(newItems);

        // This refreshes the full RecyclerView.
        notifyDataSetChanged();
    }

    // This method returns item data
    // from a selected position.
    public News_items getItemAt(int position) {
        return items.get(position);
    }

    // This method removes an item from the list.
    public void removeAt(int position) {

        // I check that the position is valid.
        if (position >= 0 && position < items.size()) {
            items.remove(position);

            // This updates RecyclerView after deletion.
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Here, I connect XML layout with Java code.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_items, parent, false);

        // I return ViewHolder object.
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        // I get current item using position.
        News_items item = items.get(position);

        // I set image from model class.
        holder.image.setImageResource(item.getImageRes());

        // I set source name.
        holder.source.setText(item.getSource());

        // I set title text.
        holder.title.setText(item.getTitle());

        // I set description text.
        holder.description.setText(item.getDescription());

        // When I click the item, selected data is sent.
        holder.itemView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {

        // This returns total items in list.
        return items.size();
    }

    // ViewHolder class stores views of each row item.
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        // ImageView for news image.
        androidx.appcompat.widget.AppCompatImageView image;

        // TextViews for source, title, and description.
        MaterialTextView source;
        MaterialTextView title;
        MaterialTextView description;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            // I connect Java variables with XML views.
            image = itemView.findViewById(R.id.ivNews);
            source = itemView.findViewById(R.id.tvNewsSource);
            title = itemView.findViewById(R.id.tvNewsTitle);
            description = itemView.findViewById(R.id.tvNewsDescription);
        }
    }
}