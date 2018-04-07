package com.example.RecipeReady;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * This activity will allow user to type the ingredients they have and will take those ingredient values
 * to show the possible recipes that user can make out of it.
 */
 public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    //Variables for the activity_main.xml
    Button button;
    EditText editText;
    TextView textView;
    ProgressBar pb;
    String recipeIdList;
    private static final String log = "MainActivity";

    /**
     * This will create button, textview and progress bar
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);

    }

    /**
     * This function will Load user's favorite recipes reading from sharedPreferences.
     * @param view
     */
    public void loadFavoriteRecipe(View view) {

        SharedPreferences sharedPref = getSharedPreferences(Main3Activity.RECIPES_ID_LIST, Context.MODE_PRIVATE);
        recipeIdList = sharedPref.getString(Main3Activity.Favorite_Racepi_id, null);

        String[] id;
        String idList = sharedPref.getString(Main3Activity.Favorite_Racepi_id,"nothing");
        id = idList.split("/");

        for(int i = 0; i<id.length; i++){

            Log.i("idArray", id[i]);
        }

        Log.i("Share", sharedPref.getString(Main3Activity.Favorite_Racepi_id,"nothing"));

        // After reading recipes from sharedPreferences, it will move the app from MainActivity to Main2Activity
        // the intent will take the value of idList
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("idList", recipeIdList);
        this.startActivity(intent);

    }

    /**
     * this will call AscynkTas to show the list view of the recipe the user can make out of the ingredients
     * that user has typed
     * @param view
     */
    public void onClick(View view) {

        new RequestRecipe(this, editText).execute();

    }

    /**
     * complete the task
     */
    public void onTaskCompleted(){}
}


