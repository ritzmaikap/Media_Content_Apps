package com.example.media_contents_apps.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.adapters.RelatedAdapter;
import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.NewsItem;
import com.example.media_contents_apps.utils.BookmarkManager;

import java.util.List;

public class DetailFragment extends Fragment {

    public interface DetailFragmentListener {
        void onRelatedNewsSelected(NewsItem item);
        void onBackToHome();
    }

    private static final String ARG_NEWS_ID = "news_id";

    private DetailFragmentListener listener;
    private NewsItem currentItem;

    private AppCompatImageView ivDetail;
    private TextView tvDetailTitle;
    private TextView tvDetailDescription;
    private RecyclerView rvRelated;
    private Button btnBack;
    private Button btnBookmark;

    public static DetailFragment newInstance(int newsId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentListener) {
            listener = (DetailFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivDetail = view.findViewById(R.id.ivDetail);
        tvDetailTitle = view.findViewById(R.id.tvDetailTitle);
        tvDetailDescription = view.findViewById(R.id.tvDetailDescription);
        rvRelated = view.findViewById(R.id.rvRelated);
        btnBack = view.findViewById(R.id.btnBack);
        btnBookmark = view.findViewById(R.id.btnBookmark);

        int newsId = getArguments() != null ? getArguments().getInt(ARG_NEWS_ID, -1) : -1;
        currentItem = DummyData.getNewsById(newsId);

        if (currentItem == null) {
            return;
        }

        ivDetail.setImageResource(currentItem.getImageRes());
        tvDetailTitle.setText(currentItem.getTitle());
        tvDetailDescription.setText(currentItem.getDescription());

        updateBookmarkButton();

        btnBack.setOnClickListener(v -> listener.onBackToHome());

        btnBookmark.setOnClickListener(v -> {
            BookmarkManager.toggleBookmark(requireContext(), currentItem.getId());
            updateBookmarkButton();
            Toast.makeText(requireContext(), "Bookmarks updated", Toast.LENGTH_SHORT).show();
        });

        List<NewsItem> related = DummyData.getRelatedNews(currentItem.getCategory(), currentItem.getId());
        rvRelated.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvRelated.setAdapter(new RelatedAdapter(related, item -> listener.onRelatedNewsSelected(item)));
    }

    private void updateBookmarkButton() {
        boolean bookmarked = BookmarkManager.isBookmarked(requireContext(), currentItem.getId());
        btnBookmark.setText(bookmarked ? "Remove Bookmark" : "Bookmark");
    }
}