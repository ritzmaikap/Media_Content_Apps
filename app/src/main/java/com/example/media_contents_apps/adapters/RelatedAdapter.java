package com.example.media_contents_apps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.models.NewsItem;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.RelatedViewHolder> {

    public interface OnRelatedClickListener {
        void onRelatedClick(NewsItem item);
    }

    private final List<NewsItem> items;
    private final OnRelatedClickListener listener;

    public RelatedAdapter(List<NewsItem> items, OnRelatedClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_related, parent, false);
        return new RelatedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.image.setImageResource(item.getImageRes());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onRelatedClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class RelatedViewHolder extends RecyclerView.ViewHolder {
        androidx.appcompat.widget.AppCompatImageView image;
        MaterialTextView title;
        MaterialTextView description;

        public RelatedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivRelated);
            title = itemView.findViewById(R.id.tvRelatedTitle);
            description = itemView.findViewById(R.id.tvRelatedDescription);
        }
    }
}