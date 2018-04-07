package com.example.blank;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that requests the recipes based on the ingredients that user has input
 * using recipe API through AsyncTask
 *
 * @Author: Kyungwoo Jo
 */
public class RequestRecipe extends AsyncTask<URL, Integer, Void> {

    private OnTaskCompleted listener;
    private Context context;
    List<Recipe> recipes = new ArrayList<Recipe>();
    private String[] input;
    String typedText;

    // WeakReference to access into MainActivity
    private WeakReference<MainActivity> weakref;
    // allLines will read all Json strings from URL and save them into it.
    String allLines = "";

    /**
     * this will initialize WeakReference.
     * @param activity
     * @param editText
     */
    RequestRecipe(MainActivity activity, EditText editText) {

        weakref = new WeakReference<MainActivity>(activity);
        context = activity;
        typedText = editText.getText().toString();
        input = typedText.split("\\,\\s?");
    }

    /**
     * This function will access into the URL given from "recipe nutrition API"
     * to pull json that holds 20 different recipes' information.
     * It will also read that Json information and save them into "allLines" using BufferReader.
     * @param urls
     * @return
     */
    protected Void doInBackground(URL... urls) {

            try {
                int i = 0;
                String theUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=";
                //https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=apples%2Cflour%2Csugar&number=20
                for(int j = 0; j < input.length; j++) {
                    theUrl = theUrl + "%2C" + input[j];
                }
                theUrl = theUrl + "&number=20";
                URL url = new URL(theUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "Z4VortkhmBmshnQP8ZDVuCWD6c6mp183oC1jsnT5HTCulZ3BDF");
                connection.setRequestProperty("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com");
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";

                // Json to String variable, view progress bar
//                weakref.get().pb.setVisibility(View.VISIBLE);
                do {
                    line = reader.readLine();

                    if (line != null) {
                        allLines += line;
//                        publishProgress(i);
                        i++;
                    }
                } while (line != null);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

    /**
     * This function will transit the app from MainActivity to Main2Activity
     * intent will take the values of allLines and typedText
     * @param aVoid
     */
    protected void onPostExecute(Void aVoid) {

            Intent intent = new Intent(context, Main2Activity.class);
            intent.putExtra("obj", allLines);
            intent.putExtra("input", typedText);
            weakref.get().startActivity(intent);
        weakref.get().pb.setVisibility(View.GONE);

        }
    }
