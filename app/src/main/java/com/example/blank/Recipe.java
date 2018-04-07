package com.example.blank;

/**
 * Author: Garett
 * I made this class to make a Recipe object using Json string
 */
public class Recipe {
    private String id;
    private String title;
    private String image;
    private String imageType;
    private String usedIngredientCount;
    private String missedIngredientCount;
    private String likes;

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public String getImageURL() {
        return image;
    }

    /**
     *
     * @return
     */
    public String getLikes() { return likes; }

}
