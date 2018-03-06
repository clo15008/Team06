package com.example.blank;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeLoader {

    Recipe recipe;
    JSONObject jsonObject;

    public void load()
    {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ingredients=apples%2Cflour%2Csugar")
                    .header("X-Mashape-Key", "ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw")
                    .header("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                    .asJson();

            jsonObject = response.getBody().getObject();

            Gson gson = new Gson();

            System.out.println(jsonObject.toString());

            // How to serialize multiple recipe objects using fromJason??
            recipe =  gson.fromJson(jsonObject.toString(), Recipe.class);

        } catch (UnirestException e) {
            e.printStackTrace();

        }



    }
}
