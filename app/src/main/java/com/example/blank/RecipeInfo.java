package com.example.blank;

import android.util.Log;

import java.net.URL;

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
    //private String extendedIngredients[];

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

    /*public String getExtendedIngredients() {
        String ingred = "";
        if(extendedIngredients.length > 0) {
            for (int i = 0; i < extendedIngredients.length; i++)
                ingred = ingred + extendedIngredients[i];
            Log.i("extendedIngredients","ingred: " + ingred);
            return ingred;
        }
        else
            return "extendedIngredients is empty";
    }*/
}

