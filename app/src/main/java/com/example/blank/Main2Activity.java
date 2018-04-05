package com.example.blank;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
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

public class Main2Activity extends AppCompatActivity {

    List<Recipe> recipes = new ArrayList<Recipe>();
    List<RecipeInfo> recipesInfo = new ArrayList<RecipeInfo>();
    List<String> url_list = new ArrayList<String>();
    List<String> recipe_title = new ArrayList<String>();
    List<String> recipe_likes = new ArrayList<String>();
    ListView lv;
    String idNumber;
    public String input = "";
    String recipeID;


    @Override
    /**
     * Author: Kyungwoo Jo
     * This onCreate will show user's favorite recipe list view if the user clicked "F.R" button on the MainActivity.
     * If the user click the "search" button, then onCreate will show the all possible recipe list view.
     * onCreate will distinguish whether user clicked "F.R" or "search" button by checking
     * if the getIntent().getStringExtra("input") == null)
     * if it is null then the onCreate will F'R list
     * if not, the onCreate will show possible recipe list.
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_listview);
        lv = (ListView) findViewById(R.id.listView);

        Gson gson = new Gson();

        // Check if user typed any ingreadients or not. If it is null, show user's favorite recipe list
        if (getIntent().getStringExtra("input") == null) {

            String[] id;
            String idList = getIntent().getStringExtra("idList");
            id = idList.split("/");
            Log.i("idList", idList);
            for(int i=0; i<id.length; i++){

                recipeID = id[i];
                Log.i("recipeId", recipeID);

                // Call AscynkTask which will bring the json of picture urls, titles and number of likes of the food
                //using Food nutrition Api
                new Request_Recipe2(Main2Activity.this).execute(recipeID);

            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    idNumber = recipesInfo.get(i).getId();

                    // Call Api which will show the specific recipe information depends on which item
                    // the user has clicked on the view list
                    new RequestInfo(Main2Activity.this).execute();

                }
            });
        }

        // If user typed some ingredients, show possible recipes using the ingredients that user has typed.
        else{

            try {
                String strObj = getIntent().getStringExtra("obj");
                input = getIntent().getStringExtra("input");
                JSONArray jsonarray = new JSONArray(strObj);

                for (int j = 0; j < jsonarray.length(); j++) {

                    // Seperate Json Array which contains all possible recipes into individual Json recipe object
                    JSONObject obj = jsonarray.getJSONObject(j);
                    Recipe recipe = gson.fromJson(obj.toString(), Recipe.class);
                    recipes.add(recipe);

                    // add imageURL, Title, Like into the corresponding Array List. This will help to make
                    // Customized recipe ListView
                    url_list.add(recipe.getImageURL());
                    recipe_title.add(recipe.getTitle());
                    recipe_likes.add(recipe.getLikes());
                }

                lv.setAdapter(new CustomAdator());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    idNumber = recipes.get(i).getId();

                    new RequestInfo(Main2Activity.this).execute();

                }
            });
        }
    }

    /**
     *
     * @return input
     */
    public String getInput() { return input; }

    /**
     * This is a AsyncTask class which will show the specific recipe information using API with
     * the idNumber of the food.
     */
    private static class RequestInfo extends AsyncTask<URL,Integer,Void> {

        private Context context;
        private WeakReference<Main2Activity> weakref;
        String allLines = "";

        RequestInfo(Main2Activity activity){

            context = activity;
            weakref = new WeakReference<Main2Activity>(activity);
        }


        /**
         * this function will all the Api to get the recipe information of the specific food using
         * Food - nutrition- Api with the idNumber of the food.
         */
        protected Void doInBackground(URL... uRls) {

            //REQUEST INFO FROM API
            try {
                int i = 0;
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + weakref.get().idNumber + "/information");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //gcloud API - ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw
                // kj API - BBB93pKWHNmshVQ2JNR0STYwPj7Xp1hdsyMjsnJbdNPTkS63hu
                connection.setRequestProperty("X-Mashape-Key", "Z4VortkhmBmshnQP8ZDVuCWD6c6mp183oC1jsnT5HTCulZ3BDF");
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
         * It will take the user from the second Activity to the Third Activity
         * taking the string value of allLines which contains the Josn of recipe.
         * @param aVoid
         */
        protected void onPostExecute(Void aVoid){
            //GET INTENT
            Intent intent = new Intent(context, Main3Activity.class);
            intent.putExtra("obj", allLines);
            intent.putExtra("input", weakref.get().getInput());
            weakref.get().startActivity(intent);
        }
    }

    /**
     * This AsyncTask will all the API to get the Json of  picture URls, titles and number of likes of the food
     *
     */
    public class Request_Recipe2 extends AsyncTask<String,Integer,Void> {


        private Context context;
        private WeakReference<Main2Activity> weakref;
        private String allLines = "";

        Request_Recipe2(Main2Activity activity) {

            context = activity;
            weakref = new WeakReference<Main2Activity>(activity);
        }

        /**
         * This doInBackground will save all Json stings into allLines and turn them into recipeInfo object
         *
         * @param strings
         * @return
         */
        protected Void doInBackground(String... strings) {

            Gson gson = new Gson();

            //REQUEST INFO FROM API
            try {
                int i = 0;
                //479101
                Log.i("check" ,strings[0]);
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + strings[0] + "/information");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //gcloud API - ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw
                // kj API - BBB93pKWHNmshVQ2JNR0STYwPj7Xp1hdsyMjsnJbdNPTkS63hu
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

                } while (line != null);

                Log.i("test", allLines);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RecipeInfo recipeInfo = gson.fromJson(allLines, RecipeInfo.class);
            recipesInfo.add(recipeInfo);
            url_list.add(recipeInfo.getImageURL());
            recipe_title.add(recipeInfo.getTitle());
            recipe_likes.add(recipeInfo.getLikes());

            return null;
        }

        /**
         * It will set a base adaptor to the list view to show all user's favorite recipes.
         * @param aVoid
         */
        protected void onPostExecute(Void aVoid) {

            lv.setAdapter( new CustomAdator());

        }

    }

    /**
     * This class will make baseAdapter using picture urls, recipe titles and number of likes Array lists.
     *
     */
    class CustomAdator extends BaseAdapter {

        /**
         * This will determine how many time getView function would be called
         * @return url_list
         */
        public int getCount() {

            Log.i("urlList", Integer.toString(url_list.size()));
            return url_list.size();

        }

        /**
         *
         * @param i
         * @return
         */
        public Object getItem(int i) {
            return null;
        }

        /**
         *
         * @param i
         * @return
         */
        public long getItemId(int i) {
            return 0;
        }

        /**
         * This will make customized listView
         * @param i
         * @param view
         * @param viewGroup
         * @return
         */
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.costomized_listview,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView titleText = (TextView)view.findViewById(R.id.textView_title);
            TextView likesText = (TextView)view.findViewById(R.id.textView_likes);
            Log.i("ithRecipe", Integer.toString(i) + " " + recipe_title.get(i));
            Picasso.with(getApplicationContext()).load(url_list.get(i)).into(imageView);
            titleText.setText(recipe_title.get(i));
            likesText.setText("likes: " + recipe_likes.get(i));
            return view;

        }
    }


}