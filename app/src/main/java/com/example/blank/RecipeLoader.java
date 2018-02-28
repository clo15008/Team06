package com.example.blank;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gclou on 2/27/2018.
 */

public class RecipeLoader {
    public void load()
    {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ingredients=apples%2Cflour%2Csugar")
                    .header("X-Mashape-Key", "ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw")
                    .header("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
