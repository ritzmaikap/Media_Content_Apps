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
import com.example.media_contents_apps.adapters.News;
import com.example.media_contents_apps.models.News_items;
import com.example.media_contents_apps.utils.Manage_Bookmark;

import java.util.List;

// This fragment is used to display all bookmarked news items.
public class Bookmarks extends Fragment {

    // This interface is used to send bookmark actions
    // from this fragment to the activity.
    public interface BookmarksFragmentListener {

        // This method opens the selected bookmarked item.
        void onBookmarkSelected(News_items item);

        // This method is used when back button is pressed.
        void onBookmarksBack();
    }

    // This variable stores the listener object.
    private BookmarksFragmentListener listener;

    // RecyclerView is used to show bookmarked news.
    private RecyclerView rvBookmarks;

    // This TextView is shown when there are no bookmarks.
    private TextView tvEmpty;

    // This button is used to go back.
    private Button btnBack;

    // This adapter connects bookmark data to RecyclerView.
    private News adapter;

    // This method creates a new instance of the fragment.
    public static Bookmarks newInstance() {
        return new Bookmarks();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Here, I check whether the activity
        // has implemented the required listener interface.
        if (context instanceof BookmarksFragmentListener) {
            listener = (BookmarksFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Here, I connect this fragment with its XML layout file.
        return inflater.inflate(R.layout.bookmarks_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // I connect Java variables with XML views.
        rvBookmarks = view.findViewById(R.id.rvBookmarks);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        btnBack = view.findViewById(R.id.btnBackBookmarks);

        // When back button is clicked, I return to previous screen.
        btnBack.setOnClickListener(v -> listener.onBookmarksBack());

        // I display bookmarked items in a grid with 2 columns.
        rvBookmarks.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // ItemTouchHelper is used to allow swipe actions on RecyclerView items.
        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                ) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {

                        // Drag and drop is not used here,
                        // so I return false.
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                        // If adapter is not ready, I stop the action.
                        if (adapter == null) return;

                        // I get the swiped item position.
                        int position = viewHolder.getBindingAdapterPosition();

                        // I get the selected bookmarked item.
                        News_items item = adapter.getItemAt(position);

                        // I remove bookmark status using item id.
                        Manage_Bookmark.toggleBookmark(requireContext(), item.getId());

                        // I remove the item from the RecyclerView list.
                        adapter.removeAt(position);

                        // I check whether the list has become empty.
                        checkEmptyState();
                    }
                }
        ).attachToRecyclerView(rvBookmarks);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Whenever this fragment becomes visible again,
        // I reload bookmarked news.
        setupBookmarks();
    }

    // This method loads all bookmarked items
    // and displays them in the RecyclerView.
    private void setupBookmarks() {

        // I get all bookmarked news items.
        List<News_items> bookmarked = Manage_Bookmark.getBookmarkedNews(requireContext());

        // I create adapter and handle item click event.
        adapter = new News(bookmarked, item -> listener.onBookmarkSelected(item));

        // I set adapter to RecyclerView.
        rvBookmarks.setAdapter(adapter);

        // I check whether the empty message should be shown.
        checkEmptyState();
    }

    // This method shows or hides the empty message
    // depending on whether bookmark list has items.
    private void checkEmptyState() {
        if (adapter == null || adapter.getItemCount() == 0) {

            // If there are no bookmarks, I show the empty message.
            tvEmpty.setVisibility(View.VISIBLE);
        } else {

            // If bookmarks exist, I hide the empty message.
            tvEmpty.setVisibility(View.GONE);
        }
    }
}