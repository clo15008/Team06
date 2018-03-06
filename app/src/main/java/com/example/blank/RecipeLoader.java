package com.example.blank;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeLoader {

    private Recipe recipe;
    private JSONObject jsonObject;

    public void loadRecipe() {
        try {
            // Create a stream to the URL
            URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients/")
                    .header("X-Mashape-Key", "ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw")
                    .header("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read all data from the website into a single string
            String line = "";
            String allLines = "";
            do {
                line = reader.readLine();
                if (line != null) {
                    allLines += line;
                }
            }
            while (line != null);

            // Create the EarthquakeList object from the JSON data
            Gson gson = new Gson();
            recipe = gson.fromJson(allLines, Recipe.class);
        }
        catch (MalformedURLException murle) {
            System.out.println(murle.getMessage());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void load()
    {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients/")
                    .header("X-Mashape-Key", "ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw")
                    .header("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                    .asJson();


            // THIS CODE MIGHT BE AN ALTERNATE FORM OF THE ABOVE REQUEST
            /*HttpResponse<Recipe> r = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients/" + "milk").asObject(Recipe.class);
            Recipe re = r.getBody();
            System.out.println(re);*/

            jsonObject = response.getBody().getObject();

            Gson gson = new Gson();

            System.out.println(jsonObject.toString());

            // How to serialize multiple recipe objects using fromJason??
            recipe =  gson.fromJson(jsonObject.toString(), Recipe.class);

        } catch (UnirestException e) {
            e.printStackTrace();

        }
    }
    public Recipe getRecipeList() {
        return recipe;
    }
}
