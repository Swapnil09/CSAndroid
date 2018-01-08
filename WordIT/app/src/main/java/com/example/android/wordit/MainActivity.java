package com.example.android.wordit;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.AssetManager;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int score;
    private TextView text,timer,scoreText,resultview;
    private EditText newWord;
    Button btnch,btnrest;
    private WordDictionary wordDictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            wordDictionary = new WordDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onStart(View view) {
        timer = (TextView) findViewById(R.id.timer);
        btnch = (Button) findViewById(R.id.button_check);
        btnrest = (Button) findViewById(R.id.button_restart);
        text = (TextView) findViewById(R.id.randomText);
        newWord = (EditText) findViewById(R.id.newWord);
        scoreText = (TextView) findViewById(R.id.score);
        resultview = (TextView) findViewById(R.id.results);
        score = 0;
        String word = wordDictionary.getNewLettersSequence();
        text.setText(word);
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                btnch.setVisibility(View.INVISIBLE);
                resultview.append(TextUtils.join("\n",wordDictionary.result));
            }
        }.start();
        return true;
    }

    public void onCheck(View view){
        String word = newWord.getText().toString();
        if(wordDictionary.isWord(word)){
            score = Integer.parseInt(scoreText.getText().toString());
            score = score + 5;
            scoreText.setText(""+score);
            newWord.setText("");
        }
        else{
            newWord.setText("");
        }
    }
    public void newGame(View view){
        resultview.setText("");
        btnch.setVisibility(View.VISIBLE);
        onStart(null);
    }
}
