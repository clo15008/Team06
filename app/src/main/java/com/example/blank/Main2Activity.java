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
    List<String> url_list = new ArrayList<String>();
    List<String> recipe_title = new ArrayList<String>();
    List<String> recipe_likes = new ArrayList<String>();
    ListView lv;
    String idNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_listview);
        lv = (ListView) findViewById(R.id.listView);


        Gson gson = new Gson();

        try {
            String strObj = getIntent().getStringExtra("obj");
            JSONArray jsonarray = new JSONArray(strObj);

            for (int j = 0; j < jsonarray.length(); j++) {
                JSONObject obj = jsonarray.getJSONObject(j);
                Recipe recipe = gson.fromJson(obj.toString(), Recipe.class);
                recipes.add(recipe);
                url_list.add(recipe.getImageURL());
                recipe_title.add(recipe.getTitle());
                recipe_likes.add(recipe.getLikes());

                lv.setAdapter( new CustomAdator());

            }


        }  catch (JSONException e) {
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

    public void callInfo (View view){

        new Main2Activity.RequestInfo(this).execute();
    }


    class CustomAdator extends BaseAdapter {

        @Override
        public int getCount() {
            return url_list.size();
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
            view = getLayoutInflater().inflate(R.layout.costomized_listview,null);

            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView titleText = (TextView)view.findViewById(R.id.textView_title);
            TextView likesText = (TextView)view.findViewById(R.id.textView_likes);

            Picasso.with(getApplicationContext()).load(url_list.get(i)).into(imageView);
            titleText.setText(recipe_title.get(i));
            likesText.setText("likes: " + recipe_likes.get(i));


            return view;

        }
    }

    private static class RequestInfo extends AsyncTask<URL,Integer,Void> {

        private Context context;
        private WeakReference<Main2Activity> weakref;
        String allLines = "";

        RequestInfo(Main2Activity activity){

            context = activity;
            weakref = new WeakReference<Main2Activity>(activity);
        }

        @Override
        protected Void doInBackground(URL... urls) {

            //REQUEST INFO FROM API
            try {
                int i = 0;
                //479101
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + weakref.get().idNumber + "/information");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //gcloud API - ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw
                // kj API - BBB93pKWHNmshVQ2JNR0STYwPj7Xp1hdsyMjsnJbdNPTkS63hu
                connection.setRequestProperty("X-Mashape-Key", "ittcmgzIz1mshRfHT4GfOzDIgM4rp1bdJ59jsnI7kl8mVjgxCw");
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

        protected void onProgressUpdate(Integer... values){

            if(weakref.get() != null){

                //weakref.get().pb.setProgress(values[0]);
            }
        }

        protected void onPostExecute(Void aVoid){
            //GET INTENT
            Intent intent = new Intent(context, Main3Activity.class);
            intent.putExtra("obj", allLines);
            weakref.get().startActivity(intent);
        }
    }
}