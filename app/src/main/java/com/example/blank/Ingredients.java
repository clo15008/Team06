package com.example.blank;

import java.net.URL;

/**
 * Ingredients class holds data of a single ingredient for a selected recipe.
 *
 * @author gcloud
 *
 */

public class Ingredients {
    private String id;
    private String image;
    private String name;
    private String amount;
    private String unit;

    /**
     *
     * @return The string variable id which is the recipe id.
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return The string variable image which holds the image url for an ingredient
     */
    public String getImage() { return image; }

    /**
     *
     * @return The string variable name which holds the name of an ingredient.
     */
    public String getName() { return name; }

    /**
     *
     * @return The string variable amount which holds the unit amount of a specific ingredient used
     *  in the recipe.
     */
    public String getAmount() { return amount; }

    /**
     *
     * @return The string variable unit which holds the unit data type used.
     */
    public String getUnit() { return unit; }
}
