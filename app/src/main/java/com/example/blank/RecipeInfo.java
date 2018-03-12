package com.example.blank;

import java.net.URL;

/**
 * Created by gclou on 3/6/2018.
 */

public class RecipeInfo {
    private String id;
    private String title;
    private URL imageURL;
    private String imageType;
    private String instructions;
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

    public String getInstructions() { return instructions; }

    public String getLikes() { return likes; }

}