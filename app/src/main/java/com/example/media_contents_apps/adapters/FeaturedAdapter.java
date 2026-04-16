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


public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    public interface OnFeaturedClickListener {
        void onFeaturedClick(NewsItem item);
    }

    private final List<NewsItem> items;
    private final OnFeaturedClickListener listener;

    public FeaturedAdapter(List<NewsItem> items, OnFeaturedClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured, parent, false);
        return new FeaturedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.image.setImageResource(item.getImageRes());
        holder.title.setText(item.getCategory());
        holder.itemView.setOnClickListener(v -> listener.onFeaturedClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FeaturedViewHolder extends RecyclerView.ViewHolder {
        androidx.appcompat.widget.AppCompatImageView image;
        MaterialTextView title;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivFeatured);
            title = itemView.findViewById(R.id.tvFeaturedTitle);
        }
    }
}