package com.example.blank;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {

    // Variables
    TextView title;
    TextView ingredients;
    TextView missingIngredients;
    TextView instructions;
    ImageView view;
    String needed = "";
    RecipeInfo obj;
    ArrayList<String> ar = new ArrayList<String>();
    public static final String RECIPES_ID_LIST = "USER_FAVORITE_RECIPE";
    public static final String Favorite_Racepi_id = "Favorite_Racepi_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //  Get Intent
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        String input = getIntent().getStringExtra("input");
        obj = gson.fromJson(strObj, RecipeInfo.class);

        // Break up userInput by ','
          Log.i("User Input", "input: " + input);
         String[] userInput = input.split("\\,\\s?");

        // Set variables to UI elements
        missingIngredients = (TextView) findViewById(R.id.textView2);
        title = (TextView) findViewById(R.id.textView4);
        ingredients = (TextView) findViewById(R.id.textView5);
        instructions = (TextView) findViewById(R.id.textView6);
        view = (ImageView) findViewById(R.id.imageView2);

        // Set title
        title.setText(obj.getTitle());

        if(getIntent().getStringExtra("input") != "");
        {
            String ingred = "";
            if(obj.getExtendedIngredients().length > 0) {
                for (int i = 0; i < obj.getExtendedIngredients().length; i++) {
                    ingred = ingred + obj.getExtendedIngredients()[i].getName() + "- " + obj.getExtendedIngredients()[i].getAmount() +
                            " " + obj.getExtendedIngredients()[i].getUnit() + "\n";
                    if (!Arrays.asList(userInput).contains(obj.getExtendedIngredients()[i].getName())) {
                        ar.add(obj.getExtendedIngredients()[i].getName());
                    }
                }
                ingredients.setText(ingred);
            }
            else {
                ingredients.setText("Sorry, listed ingredients not available.");
            }

        }

        // Set needed ingredients
        String need = "";
        if(ar != null) {
            for (int i = 0; i < ar.size(); i++) {
                need = need + ar.get(i) + ", ";
            }
            missingIngredients.setText(need);
        }
        else {
            missingIngredients.setText("You have all the ingredients for this recipe.");
        }

        // Set instructions
        if(obj.getInstructions()!= null) {
            String finalMod = "";

            // Modify contents of instructions removing excess spacing, newlines and tab characters.
            String modified = obj.getInstructions().replace("\n", "");
            modified = modified.replaceAll("\\s{2,}?", "");
            modified = modified.replace("\t","");
            modified = modified.replace("Instructions","");
            String[] addBreaks = modified.split("\\.");

            for (int i = 0; i < addBreaks.length; i++) {
                finalMod = finalMod + addBreaks[i] + ".\n\n";
            }
            instructions.setText(finalMod);
        }
        else{
            instructions.setText("Sorry, no instructions available for this recipe.");
        }

        // Get picture of dish or recipe
        Picasso.with(getApplicationContext()).load(obj.getImageURL()).into(view);
        Log.i("Picture", "getImageURL()" + obj.getImageURL());


    }

    /**
     * This button will save the the recipe's id number with '/' into sharedPreference so show
     * in the user's user's favorite recipe list view
     * @param view
     */
    public void save_Racipe(View view){

        // Added '/' to split a sequence of id lists using Split() function.
        String id = obj.getId() + '/';

        SharedPreferences sharedPref = getSharedPreferences(RECIPES_ID_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Favorite_Racepi_id, sharedPref.getString(Favorite_Racepi_id,null) + id);
        editor.apply();
        Log.i("Share", sharedPref.getString(Favorite_Racepi_id,"nothing"));

        Toast.makeText(this, "Successfully Saved the recipe", Toast.LENGTH_SHORT).show();

    }

    }

