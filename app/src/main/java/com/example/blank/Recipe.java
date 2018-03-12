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
    private String usedIngredientCount;
    private String missedIngredientCount;
    private String likes;

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

    public String getUsedIngredientCount() { return usedIngredientCount; }

    public String getMissedIngredientCount() { return missedIngredientCount; }

    public String getLikes() { return likes; }

}
