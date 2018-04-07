package com.example.RecipeReady;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Author: Katya
 * This activity is designed to present the first screen when a user opens the app.
 * It explains how to get a recipe by inputting names of given ingredients from a user's kitchen.
 */

public class IntroActivity extends AppCompatActivity {

    Button bt;

    TextView infoDisplay;
    String content= "<p>If you want to find a recipe just simply type ingredients that you already have in your kitchen. </p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        bt = (Button) findViewById(R.id.buttonGo);

        infoDisplay = (TextView) findViewById(R.id.info);
        // set Text in TextView using fromHtml() method with version check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            infoDisplay.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        } else
            infoDisplay.setText(Html.fromHtml(content));
    }


           public void sendMessage(View view)
      {
       Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
      }
    }



