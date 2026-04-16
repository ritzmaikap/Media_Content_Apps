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
import com.example.media_contents_apps.adapters.FeaturedAdapter;
import com.example.media_contents_apps.adapters.NewsAdapter;
import com.example.media_contents_apps.data.DummyData;
import com.example.media_contents_apps.models.NewsItem;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public interface HomeFragmentListener {
        void onNewsSelected(NewsItem item);
        void onOpenBookmarks();
    }

    private HomeFragmentListener listener;
    private NewsAdapter newsAdapter;
    private List<NewsItem> allNews;

    private RecyclerView rvFeatured;
    private RecyclerView rvNews;
    private SearchView searchView;
    private MaterialAutoCompleteTextView spinnerCategory;
    private Button btnBookmarks;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvFeatured = view.findViewById(R.id.rvFeatured);
        rvNews = view.findViewById(R.id.rvNews);
        searchView = view.findViewById(R.id.searchView);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnBookmarks = view.findViewById(R.id.btnBookmarks);

        allNews = DummyData.getAllNews();

        rvFeatured.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFeatured.setAdapter(new FeaturedAdapter(DummyData.getFeaturedNews(), item -> listener.onNewsSelected(item)));

        rvNews.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        newsAdapter = new NewsAdapter(allNews, item -> listener.onNewsSelected(item));
        rvNews.setAdapter(newsAdapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                new String[]{"All", "Football", "Basketball", "Cricket"}
        );
        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerCategory.setText("All", false);

        spinnerCategory.setOnItemClickListener((parent, v, position, id) -> applyFilters());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                applyFilters();
                return true;
            }
        });

        btnBookmarks.setOnClickListener(v -> listener.onOpenBookmarks());

        applyFilters();
    }

    private void applyFilters() {
        String query = searchView.getQuery() == null ? "" : searchView.getQuery().toString().trim().toLowerCase();
        String selectedCategory = spinnerCategory.getText() == null ? "All" : spinnerCategory.getText().toString().trim();

        List<NewsItem> filtered = new ArrayList<>();

        for (NewsItem item : allNews) {
            boolean matchesCategory = selectedCategory.equalsIgnoreCase("All")
                    || item.getCategory().equalsIgnoreCase(selectedCategory);

            boolean matchesQuery = TextUtils.isEmpty(query)
                    || item.getTitle().toLowerCase().contains(query)
                    || item.getDescription().toLowerCase().contains(query)
                    || item.getSource().toLowerCase().contains(query)
                    || item.getCategory().toLowerCase().contains(query);

            if (matchesCategory && matchesQuery) {
                filtered.add(item);
            }
        }

        newsAdapter.submitList(filtered);
    }
}