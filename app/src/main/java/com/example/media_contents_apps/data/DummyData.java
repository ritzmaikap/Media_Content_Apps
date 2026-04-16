package com.example.media_contents_apps.data;

// This import should use my app package drawables,
// not android default icons.
import com.example.media_contents_apps.R;

import com.example.media_contents_apps.models.News_items;

import java.util.ArrayList;
import java.util.List;

// This class is used to store dummy news data
// for testing the application.
public class DummyData {

    // This method returns all news items.
    public static List<News_items> getAllNews() {

        // I create an empty list to store news data.
        List<News_items> list = new ArrayList<>();


        // ---------------------------------------------------
        // IMPORTANT:
        // Instead of default icons, I am using image files.
        // These images should be added inside:
        // res/drawable
        //
        // Example image names:
        // football1.jpg
        // basketball1.jpg
        // cricket1.jpg
        // etc.
        // ---------------------------------------------------


        list.add(new News_items(
                1,
                "Football Final Ends in Dramatic Shootout",
                "ABC NEWS",
                "A thrilling football final ended in a dramatic penalty shootout after both teams remained level across regular and extra time.",
                "Football",
                true,

                // Football image thumbnail
                R.drawable.football1
        ));

        list.add(new News_items(
                2,
                "Basketball Giants Dominate Season Opener",
                "THE AGE",
                "The basketball season opened with a dominant performance as the home side controlled the pace.",
                "Basketball",
                true,

                // Basketball image thumbnail
                R.drawable.basketball1
        ));

        list.add(new News_items(
                3,
                "Cricket Captain Scores Match-Winning Century",
                "7NEWS",
                "The captain led from the front with a composed century.",
                "Cricket",
                true,

                // Cricket image thumbnail
                R.drawable.cricket1
        ));

        list.add(new News_items(
                4,
                "Young Striker Impresses in Derby Clash",
                "NEWS",
                "A young striker stole the spotlight in the derby.",
                "Football",
                false,

                // Football image
                R.drawable.football2
        ));

        list.add(new News_items(
                5,
                "Late Three-Pointer Seals Basketball Thriller",
                "7NEWS",
                "A last-second three-pointer capped an intense basketball contest.",
                "Basketball",
                false,

                // Basketball image
                R.drawable.basketball2
        ));

        list.add(new News_items(
                6,
                "Fast Bowler Takes Five Wickets in Collapse",
                "ABC NEWS",
                "A fiery spell from the fast bowler triggered a dramatic collapse.",
                "Cricket",
                false,

                // Cricket image
                R.drawable.cricket2
        ));

        list.add(new News_items(
                7,
                "Women’s Football Team Extends Unbeaten Run",
                "THE AGE",
                "Another disciplined performance helped the women’s football team extend their unbeaten streak.",
                "Football",
                false,

                // Football image
                R.drawable.football3
        ));

        list.add(new News_items(
                8,
                "Basketball Coach Praises Defensive Effort",
                "SPORTS TODAY",
                "The coach praised the team’s defensive intensity.",
                "Basketball",
                false,

                // Basketball image
                R.drawable.basketball3
        ));

        list.add(new News_items(
                9,
                "Cricket Series Levelled After Tight Finish",
                "DAILY SPORTS",
                "The series is now level after a tense finish.",
                "Cricket",
                false,

                // Cricket image
                R.drawable.cricket3
        ));

        return list;
    }


    // This method returns only featured news.
    public static List<News_items> getFeaturedNews() {

        List<News_items> featured = new ArrayList<>();

        // I check all news items.
        for (News_items item : getAllNews()) {

            // If item is featured, I add it.
            if (item.isFeatured()) {
                featured.add(item);
            }
        }

        return featured;
    }


    // This method finds news using id.
    public static News_items getNewsById(int id) {

        for (News_items item : getAllNews()) {

            // I compare id values.
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }


    // This method returns related news
    // with same category except current item.
    public static List<News_items> getRelatedNews(String category, int excludeId) {

        List<News_items> related = new ArrayList<>();

        for (News_items item : getAllNews()) {

            if (item.getCategory().equalsIgnoreCase(category)
                    && item.getId() != excludeId) {

                related.add(item);
            }
        }

        return related;
    }
}