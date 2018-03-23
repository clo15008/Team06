package com.example.blank;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

    // WeakReference to access into MainActivity
    private WeakReference<MainActivity> weakref;
    // allLines will read all Json strings from URL and save them into it.
    String allLines = "";

    /**
     * This function will allow you to request the recipes using AsyncTask
     * @param activity
     */
    RequestRecipe(MainActivity activity) {

            weakref = new WeakReference<MainActivity>(activity);
            context = activity;

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
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=kimchi%2Cpork%2Cflour%2Csugar&number=20");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("X-Mashape-Key", "BBB93pKWHNmshVQ2JNR0STYwPj7Xp1hdsyMjsnJbdNPTkS63hu");
                connection.setRequestProperty("X-Mashape-Host", "spoonacular-recipe-food-nutrition-v1.p.mashape.com");
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";

                do {

                    line = reader.readLine();

                    if (line != null) {

                        allLines += line;
                        publishProgress(i);
                        i++;
                    }

                }

                while (line != null);

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
     * This fuction will show
     * @param values
     */
    protected void onProgressUpdate(Integer... values) {

            if(weakref.get() != null){

                weakref.get().pb.setProgress(values[0]);
        }

        }


        protected void onPostExecute(Void aVoid) {

            Intent intent = new Intent(context, Main2Activity.class);
            intent.putExtra("obj", allLines);
            weakref.get().startActivity(intent);

        }
    }


