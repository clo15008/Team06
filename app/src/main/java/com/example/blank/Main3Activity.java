package com.example.blank;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    // Variables
    TextView title;
    TextView ingredients;
    TextView missingIngredients;
    TextView instructions;
    ImageView view;
    //String needed = "";
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //  Get Intent
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        String input = getIntent().getStringExtra("input");
        RecipeInfo obj = gson.fromJson(strObj, RecipeInfo.class);

        // Break up userInput by ','
        String[] userInput = input.split("\\,\\s?");

        // Set variables to UI elements
        missingIngredients = (TextView) findViewById(R.id.textView2);
        title = (TextView) findViewById(R.id.textView4);
        ingredients = (TextView) findViewById(R.id.textView5);
        instructions = (TextView) findViewById(R.id.textView6);
        view = (ImageView) findViewById(R.id.imageView2);

        // Set title
        title.setText(obj.getTitle());

        // Set ingredients
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
            //Log.i("instructions", obj.getInstructions());
            String modified = obj.getInstructions().replace("\n", "");
            modified = modified.replaceAll("\\s{2,}?", "");
            modified = modified.replace("\t","");
            modified = modified.replace("Instructions","");
            System.out.print("instructions" + modified);
            Log.i("instructions", modified);
            if((modified != "") || (modified != null) || (modified != ".") || (!modified.isEmpty())) {
                String[] addBreaks = modified.split("\\.");

                for (int i = 0; i < addBreaks.length; i++) {
                    finalMod = finalMod + addBreaks[i] + ".\n\n";
                }
                instructions.setText(finalMod);
                Log.i("finalMod", finalMod);
            }
            else {
                instructions.setText("Sorry, no instructions available for this recipe.");
            }
        }
        else{
            instructions.setText("Sorry, no instructions available for this recipe.");
        }

        // Get picture of dish or recipe
        Picasso.with(getApplicationContext()).load(obj.getImageURL()).into(view);
    }
}

