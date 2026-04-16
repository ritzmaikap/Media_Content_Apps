package com.example.media_contents_apps;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.media_contents_apps.fragments.Bookmarks;
import com.example.media_contents_apps.fragments.Detail;
import com.example.media_contents_apps.fragments.Home;
import com.example.media_contents_apps.models.News_items;

// This is the main activity of the application.
// It controls fragment navigation between screens.
public class MainActivity extends AppCompatActivity implements
        Home.HomeFragmentListener,
        Detail.DetailFragmentListener,
        Bookmarks.BookmarksFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This enables edge-to-edge layout
        // so content can appear behind system bars.
        EdgeToEdge.enable(this);

        // Here, I connect the activity with its XML layout file.
        setContentView(R.layout.activity_main);

        // This adjusts padding based on system bars
        // such as status bar and navigation bar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            // I get the size of system bars.
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // I apply padding to prevent content overlap.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        // If activity is created for the first time,
        // I load the Home fragment.
        if (savedInstanceState == null) {
            loadFragment(Home.newInstance(), false);
        }
    }

    // This method is used to load a fragment
    // inside the fragment container.
    private void loadFragment(Fragment fragment, boolean addToBackStack) {

        // If true, I add fragment to back stack
        // so previous screen can be returned.
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {

            // If false, I replace fragment without saving it in back stack.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onNewsSelected(News_items item) {

        // When a news item is selected from home screen,
        // I open the detail fragment for that item.
        loadFragment(Detail.newInstance(item.getId()), true);
    }

    @Override
    public void onOpenBookmarks() {

        // When bookmarks button is clicked,
        // I open the bookmarks fragment.
        loadFragment(Bookmarks.newInstance(), true);
    }

    @Override
    public void onRelatedNewsSelected(News_items item) {

        // When a related news item is selected,
        // I open its detail fragment.
        loadFragment(Detail.newInstance(item.getId()), true);
    }

    @Override
    public void onBackToHome() {

        // This returns to the previous fragment
        // from the back stack.
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBookmarkSelected(News_items item) {

        // When a bookmarked item is selected,
        // I open its detail fragment.
        loadFragment(Detail.newInstance(item.getId()), true);
    }

    @Override
    public void onBookmarksBack() {

        // This returns to the previous fragment
        // when back is pressed in bookmarks screen.
        getSupportFragmentManager().popBackStack();
    }
}