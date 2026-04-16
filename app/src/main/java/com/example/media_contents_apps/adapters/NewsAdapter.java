package com.example.media_contents_apps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.models.NewsItem;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public interface OnNewsClickListener {
        void onNewsClick(NewsItem item);
    }

    private final List<NewsItem> items = new ArrayList<>();
    private final OnNewsClickListener listener;

    public NewsAdapter(List<NewsItem> items, OnNewsClickListener listener) {
        this.items.addAll(items);
        this.listener = listener;
    }

    public void submitList(List<NewsItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public NewsItem getItemAt(int position) {
        return items.get(position);
    }

    public void removeAt(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = items.get(position);
        holder.image.setImageResource(item.getImageRes());
        holder.source.setText(item.getSource());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        androidx.appcompat.widget.AppCompatImageView image;
        MaterialTextView source;
        MaterialTextView title;
        MaterialTextView description;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivNews);
            source = itemView.findViewById(R.id.tvNewsSource);
            title = itemView.findViewById(R.id.tvNewsTitle);
            description = itemView.findViewById(R.id.tvNewsDescription);
        }
    }
}