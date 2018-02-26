package com.example.blank;

import java.net.URL;

/**
 * Created by gclou on 2/26/2018.
 */

public class Recipe {
    private String id;
    private String title;
    private URL imageURL;
    private String imageType;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private int likes;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public String getImageType() { return imageType; }

    public int getUsedIngredientCount() { return usedIngredientCount; }

    public int getMissedIngredientCount() { return missedIngredientCount; }

    public int getLikes() { return likes; }

}
