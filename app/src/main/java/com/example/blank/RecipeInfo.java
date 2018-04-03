package com.example.blank;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * RecipeInfo recieves elements of a recipe
 */

public class RecipeInfo {
    // Private variables
    private String id;
    private String title;
    private String image;
    private String imageType;
    private String instructions;
    private String likes;
    private Ingredients[] extendedIngredients;


    /**
     *
     * @return The id variable of recipe
     */
    public String getLikes() { return likes; }


    public String getId() {
        return id;
    }

    /**
     *
     * @return The title variable of the recipe.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return The image variable of the recipe.
     */
    public String getImageURL() {
        return image;
    }

    /**
     *
     * @return The imageType variable of the recipe.
     */
    public String getImageType() { return imageType; }

    /**
     *
     * @return The instructions variable of the recipe.
     */
    public String getInstructions() { return instructions; }

    /**
     *
     * @return The extendedIngredients variable which is another class of the recipe.
     */
    public Ingredients[] getExtendedIngredients() { return extendedIngredients; }
}

