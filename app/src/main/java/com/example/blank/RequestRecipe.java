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
 * Created by Katya on 3/9/2018.
 */

public class RequestRecipe extends AsyncTask<URL, Integer, Void> {

    private OnTaskCompleted listener;
    private Context context;
    List<Recipe> recipes = new ArrayList<Recipe>();


    public RequestRecipe(OnTaskCompleted listener){
        this.listener = listener;
    }

        private WeakReference<MainActivity> weakref;
        String allLines = "";


    RequestRecipe(MainActivity activity) {

            weakref = new WeakReference<MainActivity>(activity);
            context = activity;

    }

        @Override
        protected Void doInBackground(URL... urls) {

            try {

                String ingredient = "kimchi";
                int i = 0;
                                      //https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=apples%2Cflour%2Csugar&number=20
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=apples%2Cflour%2Csugar&number=20");
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