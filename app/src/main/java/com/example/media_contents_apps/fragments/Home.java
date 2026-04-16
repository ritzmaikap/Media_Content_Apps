package com.example.media_contents_apps.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.media_contents_apps.R;
import com.example.media_contents_apps.adapters.Featured;
import com.example.media_contents_apps.adapters.News;
import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.News_items;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

// This fragment is used to display the home screen
// with featured news, all news, search, and category filter.
public class Home extends Fragment {

    // This interface is used to send actions
    // from this fragment to the activity.
    public interface HomeFragmentListener {

        // This method opens the selected news item.
        void onNewsSelected(News_items item);

        // This method opens the bookmarks screen.
        void onOpenBookmarks();
    }

    // This variable stores the listener object.
    private HomeFragmentListener listener;

    // This adapter is used for the main news list.
    private News news;

    // This list stores all available news items.
    private List<News_items> allNews;

    // RecyclerViews for featured items and all news.
    private RecyclerView rvFeatured;
    private RecyclerView rvNews;

    // SearchView is used to search news.
    private SearchView searchView;

    // This dropdown is used to filter news by category.
    private MaterialAutoCompleteTextView spinnerCategory;

    // This button opens the bookmarks screen.
    private Button btnBookmarks;

    // This method creates a new instance of the fragment.
    public static Home newInstance() {
        return new Home();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Here, I check whether the activity
        // has implemented the required listener interface.
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Here, I connect this fragment with its XML layout file.
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // I connect Java variables with XML views.
        rvFeatured = view.findViewById(R.id.rvFeatured);
        rvNews = view.findViewById(R.id.rvNews);
        searchView = view.findViewById(R.id.searchView);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnBookmarks = view.findViewById(R.id.btnBookmarks);

        // I load all news items from dummy data.
        allNews = DummyData.getAllNews();

        // I set the featured RecyclerView
        // to show items horizontally.
        rvFeatured.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // I set adapter for featured news items.
        rvFeatured.setAdapter(
                new Featured(DummyData.getFeaturedNews(), item -> listener.onNewsSelected(item))
        );

        // I set the main news RecyclerView
        // to show items in 2 columns.
        rvNews.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // I create adapter for all news items.
        news = new News(allNews, item -> listener.onNewsSelected(item));

        // I attach adapter to RecyclerView.
        rvNews.setAdapter(news);

        // I create dropdown options for category filter.
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                new String[]{"All", "Football", "Basketball", "Cricket"}
        );

        // I attach category options to dropdown.
        spinnerCategory.setAdapter(spinnerAdapter);

        // I set the default selected category as "All".
        spinnerCategory.setText("All", false);

        // When category changes, I apply filters again.
        spinnerCategory.setOnItemClickListener((parent, v, position, id) -> applyFilters());

        // I listen for search text changes and submissions.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                // When search is submitted, I filter the list.
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // When search text changes, I filter the list live.
                applyFilters();
                return true;
            }
        });

        // When bookmark button is clicked,
        // I open the bookmarks screen.
        btnBookmarks.setOnClickListener(v -> listener.onOpenBookmarks());

        // I apply filters once at the beginning
        // to show the correct data.
        applyFilters();
    }

    // This method filters news items
    // by search text and selected category.
    private void applyFilters() {

        // I get search text from SearchView.
        String query = searchView.getQuery() == null
                ? ""
                : searchView.getQuery().toString().trim().toLowerCase();

        // I get selected category from dropdown.
        String selectedCategory = spinnerCategory.getText() == null
                ? "All"
                : spinnerCategory.getText().toString().trim();

        // I create a new list for filtered results.
        List<News_items> filtered = new ArrayList<>();

        // I check every news item one by one.
        for (News_items item : allNews) {

            // This checks whether the item matches selected category.
            boolean matchesCategory = selectedCategory.equalsIgnoreCase("All")
                    || item.getCategory().equalsIgnoreCase(selectedCategory);

            // This checks whether the item matches search text.
            boolean matchesQuery = TextUtils.isEmpty(query)
                    || item.getTitle().toLowerCase().contains(query)
                    || item.getDescription().toLowerCase().contains(query)
                    || item.getSource().toLowerCase().contains(query)
                    || item.getCategory().toLowerCase().contains(query);

            // I add the item only if both conditions are true.
            if (matchesCategory && matchesQuery) {
                filtered.add(item);
            }
        }

        // I update the RecyclerView with filtered items.
        news.submitList(filtered);
    }
}