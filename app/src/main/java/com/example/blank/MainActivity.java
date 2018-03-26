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


 public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    //Variables for the activity_main.xml
    Button button;
    EditText editText;
    TextView textView;
    ProgressBar pb;
    String et;
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

        new RequestRecipe(this, editText).execute();
    }

    public void onTaskCompleted(){}
}


