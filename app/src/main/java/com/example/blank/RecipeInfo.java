package com.example.blank;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * RecipeInfo recieves
 */

public class RecipeInfo {
    private String id;
    private String title;
    private String image;
    private String imageType;
    private String instructions;
    private String likes;
    private Ingredients[] extendedIngredients;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return image;
    }

    public String getImageType() { return imageType; }

    public String getInstructions() { return instructions; }

    public Ingredients[] getExtendedIngredients() { return extendedIngredients; }
}

