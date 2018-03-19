/**
 * Created by Kyungwoo Jo on 3/14/2018.
 */
package com.example.blank;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RequestRecipeByIngredient {


        private static class RequestRecipe extends AsyncTask<URL,Integer,Void> {

            private WeakReference<MainActivity> weakref;
            private WeakReference<CustomAdator> weakrefCa;
            String allLines = "";

            RequestRecipe(MainActivity activity, CustomAdator actvity2){

                weakrefCa = new WeakReference<CustomAdator>(actvity2);
                weakref = new WeakReference<MainActivity>(activity);
            }

            @Override
            protected Void doInBackground(URL... urls) {

                int i = 0;

                try {

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

                            i++;
                            allLines += line;
                            publishProgress(i);
                        }

                    }

                    while (line != null);

                    Gson gson = new Gson();

                    JSONArray jsonarray = new JSONArray(allLines);

                    for(int j=0; j<jsonarray.length(); j++){
                        JSONObject obj = jsonarray.getJSONObject(j);
                        Recipe recipe = gson.fromJson(obj.toString(),Recipe.class);
                        List<Recipe> recipes = new ArrayList<Recipe>();
                        weakref.get().url_list.add(recipe.getImageURL());
                        weakref.get().recipe_title.add(recipe.getTitle());
                        weakref.get().recipe_likes.add(recipe.getLikes());
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

            protected void onPostExecute(Void aVoid){

                if(weakref.get() != null && weakrefCa.get() != null){

                    weakref.get().lv.setAdapter(weakrefCa.get());
                    Toast.makeText(weakref.get(), "Recipe request process is done", Toast.LENGTH_SHORT).show();

                }
            }
        }

        class CustomAdator extends BaseAdapter{

            MainActivity a;
            @Override
            public int getCount() {
                return a.url_list.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = a.getLayoutInflater().inflate(R.layout.costomized_listview,null);

                ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
                TextView titleText = (TextView)view.findViewById(R.id.textView_title);
                TextView likesText = (TextView)view.findViewById(R.id.textView_likes);

                Picasso.with(a.getApplicationContext()).load(a.url_list.get(i)).into(imageView);
                titleText.setText(a.recipe_title.get(i));
                likesText.setText("likes: " + a.recipe_likes.get(i));


                return view;

            }
        }
    }





