package com.example.media_contents_apps;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.media_contents_apps.fragments.BookmarksFragment;
import com.example.media_contents_apps.fragments.DetailFragment;
import com.example.media_contents_apps.fragments.HomeFragment;
import com.example.media_contents_apps.models.NewsItem;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.HomeFragmentListener,
        DetailFragment.DetailFragmentListener,
        BookmarksFragment.BookmarksFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            loadFragment(HomeFragment.newInstance(), false);
        }
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onNewsSelected(NewsItem item) {
        loadFragment(DetailFragment.newInstance(item.getId()), true);
    }

    @Override
    public void onOpenBookmarks() {
        loadFragment(BookmarksFragment.newInstance(), true);
    }

    @Override
    public void onRelatedNewsSelected(NewsItem item) {
        loadFragment(DetailFragment.newInstance(item.getId()), true);
    }

    @Override
    public void onBackToHome() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBookmarkSelected(NewsItem item) {
        loadFragment(DetailFragment.newInstance(item.getId()), true);
    }

    @Override
    public void onBookmarksBack() {
        getSupportFragmentManager().popBackStack();
    }
}