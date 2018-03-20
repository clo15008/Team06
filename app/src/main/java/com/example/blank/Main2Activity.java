package com.example.blank;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void callInfo (View view){

        new Main2Activity.RequestInfo(this).execute();
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
                URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/479101/information");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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