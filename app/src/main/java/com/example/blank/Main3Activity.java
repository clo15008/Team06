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
    TextView tv2;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    ImageView view;
    String needed = "";
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        String input = getIntent().getStringExtra("input");
        RecipeInfo obj = gson.fromJson(strObj, RecipeInfo.class);

        Log.i("User Input", "input: " + input);
        String[] userInput = input.split("\\,\\s?");

        //Log.i("ExtendedIngredients","value: " + obj.getExtendedIngredients());

        tv2 = (TextView) findViewById(R.id.textView2);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        view = (ImageView) findViewById(R.id.imageView2);

        tv4.setText(obj.getTitle());

        //GET INGREDIENTS

        String ingred = "";
        if(obj.getExtendedIngredients().length > 0) {
            for (int i = 0; i < obj.getExtendedIngredients().length; i++) {
                ingred = ingred + obj.getExtendedIngredients()[i].getName() + "- " + obj.getExtendedIngredients()[i].getAmount() +
                        " " + obj.getExtendedIngredients()[i].getUnit() + "\n";
                if (!Arrays.asList(userInput).contains(obj.getExtendedIngredients()[i].getName())) {
                    ar.add(obj.getExtendedIngredients()[i].getName());
                }
            }
            tv5.setText(ingred);
        }

        String need = "";
        if(!ar.isEmpty()) {
            for (int i = 0; i < ar.size(); i++) {
                need = need + ", " + ar.get(i);
            }
            tv2.setText(need);
        }
        tv6.setText(obj.getInstructions());

        Picasso.with(getApplicationContext()).load(obj.getImageURL()).into(view);
        Log.i("Picture", "getImageURL()" + obj.getImageURL());


    }
    }

