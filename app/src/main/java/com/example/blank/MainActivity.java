package com.example.blank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

//should it be abstract??????
//Multi Auto Complete TextView ---> dataBase


 public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    //Variables for the activity_main.xml
    Button button;
    EditText editText;
    TextView textView;
    ProgressBar pb;
    String recipeIdList;
    private static final String log = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);

    }


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

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("idList", recipeIdList);
        this.startActivity(intent);

    }


    public void onClick(View view) {

        new RequestRecipe(this, editText).execute();

    }

    public void onTaskCompleted(){}
}


