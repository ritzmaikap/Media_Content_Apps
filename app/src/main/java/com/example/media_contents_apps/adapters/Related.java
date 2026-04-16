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

// This adapter class is used to display related news items
// inside a RecyclerView.
public class Related extends RecyclerView.Adapter<Related.RelatedViewHolder> {

    // This interface is used to handle click events
    // when a related item is selected.
    public interface OnRelatedClickListener {
        void onRelatedClick(News_items item);
    }

    // This list stores all related news items.
    private final List<News_items> items;

    // This object listens for click events on items.
    private final OnRelatedClickListener listener;

    // Constructor used to receive the list of related items
    // and the click listener.
    public Related(List<News_items> items, OnRelatedClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Here, I connect the XML layout file with Java code.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.related_items, parent, false);

        // I return a new ViewHolder object.
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, int position) {

        // I get the current item based on its position.
        News_items item = items.get(position);

        // I set the image for the related news item.
        holder.image.setImageResource(item.getImageRes());

        // I set the title text.
        holder.title.setText(item.getTitle());

        // I set the description text.
        holder.description.setText(item.getDescription());

        // When I click the item, the selected item is passed.
        holder.itemView.setOnClickListener(v -> listener.onRelatedClick(item));
    }

    @Override
    public int getItemCount() {

        // This returns the total number of items in the list.
        return items.size();
    }

    // ViewHolder class is used to hold the views
    // for each related item.
    static class RelatedViewHolder extends RecyclerView.ViewHolder {

        // ImageView for the related item image.
        androidx.appcompat.widget.AppCompatImageView image;

        // TextViews for title and description.
        MaterialTextView title;
        MaterialTextView description;

        public RelatedViewHolder(@NonNull View itemView) {
            super(itemView);

            // I connect Java variables with XML views.
            image = itemView.findViewById(R.id.ivRelated);
            title = itemView.findViewById(R.id.tvRelatedTitle);
            description = itemView.findViewById(R.id.tvRelatedDescription);
        }
    }
}