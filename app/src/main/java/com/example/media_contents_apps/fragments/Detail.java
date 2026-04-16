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
import com.example.media_contents_apps.adapters.Related;
import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.News_items;
import com.example.media_contents_apps.utils.Manage_Bookmark;

import java.util.List;

// This fragment is used to display full details
// of one selected news item.
public class Detail extends Fragment {

    // This interface is used to send actions
    // from this fragment to the activity.
    public interface DetailFragmentListener {

        // This method opens a selected related news item.
        void onRelatedNewsSelected(News_items item);

        // This method returns to the home screen.
        void onBackToHome();
    }

    // This key is used to store and read news id
    // from the fragment arguments.
    private static final String ARG_NEWS_ID = "news_id";

    // This variable stores the listener object.
    private DetailFragmentListener listener;

    // This variable stores the currently selected news item.
    private News_items currentItem;

    // These views are used in the detail screen.
    private AppCompatImageView ivDetail;
    private TextView tvDetailTitle;
    private TextView tvDetailDescription;
    private RecyclerView rvRelated;
    private Button btnBack;
    private Button btnBookmark;

    // This method creates a new fragment instance
    // and sends the selected news id.
    public static Detail newInstance(int newsId) {
        Detail fragment = new Detail();
        Bundle args = new Bundle();

        // I store the news id inside the bundle.
        args.putInt(ARG_NEWS_ID, newsId);

        // I attach bundle data to the fragment.
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Here, I check whether the activity
        // has implemented the required listener interface.
        if (context instanceof DetailFragmentListener) {
            listener = (DetailFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Here, I connect this fragment with its XML layout file.
        return inflater.inflate(R.layout.detail_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // I connect Java variables with XML views.
        ivDetail = view.findViewById(R.id.ivDetail);
        tvDetailTitle = view.findViewById(R.id.tvDetailTitle);
        tvDetailDescription = view.findViewById(R.id.tvDetailDescription);
        rvRelated = view.findViewById(R.id.rvRelated);
        btnBack = view.findViewById(R.id.btnBack);
        btnBookmark = view.findViewById(R.id.btnBookmark);

        // I read the news id from fragment arguments.
        int newsId = getArguments() != null
                ? getArguments().getInt(ARG_NEWS_ID, -1)
                : -1;

        // I find the selected news item using its id.
        currentItem = DummyData.getNewsById(newsId);

        // If no item is found, I stop further execution.
        if (currentItem == null) {
            return;
        }

        // I display the thumbnail image.
        // This will show a real picture if imageRes points to drawable images.
        ivDetail.setImageResource(currentItem.getImageRes());

        // I display the news title.
        tvDetailTitle.setText(currentItem.getTitle());

        // I display the news description.
        tvDetailDescription.setText(currentItem.getDescription());

        // I update bookmark button text
        // based on current bookmark state.
        updateBookmarkButton();

        // When back button is clicked,
        // I return to the home screen.
        btnBack.setOnClickListener(v -> listener.onBackToHome());

        // When bookmark button is clicked,
        // I add or remove the bookmark.
        btnBookmark.setOnClickListener(v -> {
            Manage_Bookmark.toggleBookmark(requireContext(), currentItem.getId());

            // I refresh the button text after updating bookmark status.
            updateBookmarkButton();

            // I show a short message to confirm the action.
            Toast.makeText(requireContext(), "Bookmarks updated", Toast.LENGTH_SHORT).show();
        });

        // I get related news from the same category,
        // except the current selected item.
        List<News_items> related = DummyData.getRelatedNews(
                currentItem.getCategory(),
                currentItem.getId()
        );

        // I set RecyclerView to show related items
        // in a vertical list.
        rvRelated.setLayoutManager(new LinearLayoutManager(requireContext()));

        // I set adapter for related news items
        // and handle item click events.
        rvRelated.setAdapter(new Related(related, item -> listener.onRelatedNewsSelected(item)));
    }

    // This method updates the bookmark button text
    // depending on whether the current item is bookmarked.
    private void updateBookmarkButton() {

        // I check bookmark status of current item.
        boolean bookmarked = Manage_Bookmark.isBookmarked(requireContext(), currentItem.getId());

        // I change button text based on saved state.
        btnBookmark.setText(bookmarked ? "Remove Bookmark" : "Bookmark");
    }
}