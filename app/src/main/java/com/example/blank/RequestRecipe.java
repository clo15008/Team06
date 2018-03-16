package com.example.blank;

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

    public RequestRecipe(OnTaskCompleted listener){
        this.listener = listener;
    }

        private WeakReference<MainActivity> weakref;
        String allLines = "";
        List<Recipe> recipes = new ArrayList<Recipe>();

        RequestRecipe(MainActivity activity) {

            weakref = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected Void doInBackground(URL... urls) {

            try {

                int i = 0;
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ranking=1&ingredients=apples%2Cflour%2Csugar&number=5");
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

                Gson gson = new Gson();

                JSONArray jsonarray = new JSONArray(allLines);

                for (int j = 0; i < jsonarray.length(); j++) {
                    JSONObject obj = jsonarray.getJSONObject(j);
                    Recipe recipe = gson.fromJson(obj.toString(), Recipe.class);
                    recipes.add(recipe);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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

            listener.onTaskCompleted();

        }

    }


