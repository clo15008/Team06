package com.example.blank;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Main2Activity extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
    }

    public void callInfo (View view){

        new Main2Activity.RequestInfo(this).execute();
    }

    private static class RequestInfo extends AsyncTask<URL,Integer,Void> {

        private WeakReference<Main2Activity> weakref;
        String allLines = "";
        //RecipeInfo info = new RecipeInfo();
        RecipeInfo info = new RecipeInfo();

        RequestInfo(Main2Activity activity){

            weakref = new WeakReference<Main2Activity>(activity);
        }

        @Override
        protected Void doInBackground(URL... urls) {


            try {

                int i = 0;
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/479101/information");
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

                info = gson.fromJson(allLines,RecipeInfo.class);
                //info = gson.fromJson(obj.toString(),RecipeInfo.class);
                //info.add(rInfo);


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

            if(weakref.get() != null){

                //weakref.get().pb.setProgress(0);
                //weakref.get().tv1.setText(info.getExtendedIngredients());
                weakref.get().tv2.setText(info.getTitle());
                weakref.get().tv3.setText(info.getInstructions());
                Toast.makeText(weakref.get(), "Recipe request process is done", Toast.LENGTH_SHORT).show();

            }
        }
    }
}