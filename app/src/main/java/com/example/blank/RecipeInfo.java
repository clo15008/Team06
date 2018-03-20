package com.example.blank;

import android.util.Log;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Katya on 3/12/2018.
 */

public class RecipeInfo {
    private String id;
    private String title;
    private URL imageURL;
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

    public URL getImageURL() {
        return imageURL;
    }

    public String getImageType() { return imageType; }

    public String getInstructions() { return instructions; }

    public Ingredients[] getExtendedIngredients() { return extendedIngredients; }
}

