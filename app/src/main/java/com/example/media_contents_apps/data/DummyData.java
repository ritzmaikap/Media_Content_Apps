package com.example.media_contents_apps.data;

import android.R;

import com.example.media_contents_apps.models.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<NewsItem> getAllNews() {
        List<NewsItem> list = new ArrayList<>();

        list.add(new NewsItem(
                1,
                "Football Final Ends in Dramatic Shootout",
                "ABC NEWS",
                "A thrilling football final ended in a dramatic penalty shootout after both teams remained level across regular and extra time. The winning side held their nerve under pressure and lifted the trophy in front of a packed crowd.",
                "Football",
                true,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                2,
                "Basketball Giants Dominate Season Opener",
                "THE AGE",
                "The basketball season opened with a dominant performance as the home side controlled the pace, won the rebounding battle, and cruised to a convincing victory.",
                "Basketball",
                true,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                3,
                "Cricket Captain Scores Match-Winning Century",
                "7NEWS",
                "The captain led from the front with a composed century, guiding the team through a tricky chase and sealing an important win in the final overs.",
                "Cricket",
                true,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                4,
                "Young Striker Impresses in Derby Clash",
                "NEWS",
                "A young striker stole the spotlight in the derby with sharp movement, confident finishing, and a standout display that may secure a regular starting spot.",
                "Football",
                false,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                5,
                "Late Three-Pointer Seals Basketball Thriller",
                "7NEWS",
                "A last-second three-pointer capped an intense basketball contest, sending the crowd into celebration and giving the visitors a memorable road win.",
                "Basketball",
                false,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                6,
                "Fast Bowler Takes Five Wickets in Collapse",
                "ABC NEWS",
                "A fiery spell from the fast bowler triggered a dramatic batting collapse, swinging the momentum of the cricket match in just a few overs.",
                "Cricket",
                false,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                7,
                "Women’s Football Team Extends Unbeaten Run",
                "THE AGE",
                "Another disciplined performance helped the women’s football team extend their unbeaten streak, with strong defending and sharp counterattacks proving decisive.",
                "Football",
                false,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                8,
                "Basketball Coach Praises Defensive Effort",
                "SPORTS TODAY",
                "The coach highlighted the team’s discipline and defensive intensity after holding the opposition to one of their lowest scores of the season.",
                "Basketball",
                false,
                R.drawable.ic_menu_gallery
        ));

        list.add(new NewsItem(
                9,
                "Cricket Series Levelled After Tight Finish",
                "DAILY SPORTS",
                "The series is now level after a tense finish where disciplined bowling and smart field placements helped defend a modest total.",
                "Cricket",
                false,
                R.drawable.ic_menu_gallery
        ));

        return list;
    }

    public static List<NewsItem> getFeaturedNews() {
        List<NewsItem> featured = new ArrayList<>();
        for (NewsItem item : getAllNews()) {
            if (item.isFeatured()) {
                featured.add(item);
            }
        }
        return featured;
    }

    public static NewsItem getNewsById(int id) {
        for (NewsItem item : getAllNews()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static List<NewsItem> getRelatedNews(String category, int excludeId) {
        List<NewsItem> related = new ArrayList<>();
        for (NewsItem item : getAllNews()) {
            if (item.getCategory().equalsIgnoreCase(category) && item.getId() != excludeId) {
                related.add(item);
            }
        }
        return related;
    }
}