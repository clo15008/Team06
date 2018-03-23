package com.example.blank;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Main3Activity extends AppCompatActivity {
    TextView tv4;
    TextView tv5;
    TextView tv6;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("obj");
        RecipeInfo obj = gson.fromJson(strObj, RecipeInfo.class);

        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);

        tv4.setText(obj.getTitle());

        //GET INGREDIENTS
        String ingred = "";
        if(obj.getExtendedIngredients().length > 0) {
            for (int i = 0; i < obj.getExtendedIngredients().length; i++) {
                /*double d = 0;
                DecimalFormat twoDForm = new DecimalFormat("#.####");
                d = Double.valueOf(twoDForm.format(obj.getExtendedIngredients()[i].getUnit()));*/

                ingred = ingred + obj.getExtendedIngredients()[i].getName() + "- " + obj.getExtendedIngredients()[i].getAmount() +
                        " " + obj.getExtendedIngredients()[i].getUnit() + "\n";
            }
            tv5.setText(ingred);
        }

        tv6.setText(obj.getInstructions());
    }
}
