package com.example.blank;

import java.net.URL;

/**
 * Created by gclou on 3/19/2018.
 */

public class Ingredients {
    private String id;
    private URL image;
    private String name;
    private String amount;
    private String unit;
    //private String extendedIngredients[];

    public String getId() {
        return id;
    }

    public URL getImage() {
        return image;
    }

    public String getName() { return name; }

    public String getAmount() { return amount; }

    public String getUnit() { return unit; }
}
