package com.example.blank;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//should it be abstract??????
//Multi Auto Complete TextView ---> dataBase


 public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    //Variables for the activity_main.xml
    Button button;
    EditText editText;
    TextView textView;
    ProgressBar pb;
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


    public void getInput() {

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("getInput", editText.getText().toString());
        startActivity(intent);
        Log.i(MainActivity.log,"message");

    }


    public void onClick(View view) {

        new RequestRecipe(this).execute();
    }

    public void onTaskCompleted(){}
}


