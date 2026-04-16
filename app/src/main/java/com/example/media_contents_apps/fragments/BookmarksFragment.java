package com.example.media_contents_apps.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.adapters.NewsAdapter;
import com.example.media_contents_apps.models.NewsItem;
import com.example.media_contents_apps.utils.BookmarkManager;

import java.util.List;

public class BookmarksFragment extends Fragment {

    public interface BookmarksFragmentListener {
        void onBookmarkSelected(NewsItem item);
        void onBookmarksBack();
    }

    private BookmarksFragmentListener listener;
    private RecyclerView rvBookmarks;
    private TextView tvEmpty;
    private Button btnBack;
    private NewsAdapter adapter;

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BookmarksFragmentListener) {
            listener = (BookmarksFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvBookmarks = view.findViewById(R.id.rvBookmarks);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnBack = view.findViewById(R.id.btnBackBookmarks);

        btnBack.setOnClickListener(v -> listener.onBookmarksBack());

        rvBookmarks.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (adapter == null) return;

                int position = viewHolder.getBindingAdapterPosition();
                NewsItem item = adapter.getItemAt(position);
                BookmarkManager.toggleBookmark(requireContext(), item.getId());
                adapter.removeAt(position);
                checkEmptyState();
            }
        }).attachToRecyclerView(rvBookmarks);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupBookmarks();
    }

    private void setupBookmarks() {
        List<NewsItem> bookmarked = BookmarkManager.getBookmarkedNews(requireContext());
        adapter = new NewsAdapter(bookmarked, item -> listener.onBookmarkSelected(item));
        rvBookmarks.setAdapter(adapter);
        checkEmptyState();
    }

    private void checkEmptyState() {
        if (adapter == null || adapter.getItemCount() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }
}