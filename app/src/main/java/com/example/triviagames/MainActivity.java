package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int MAX_SCORES_AMOUNT = 5;

    private EditText et_nickName;
    private Button button_start;
    private Button button_settings;
    private TextView tv_maxScore;
    private TextView tv_error;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });
        //System.err.println("DATABASE NAME: " + dbHelper.getDatabaseName());
        //DEBUG
        //ArrayAdapter questionsAdapter = new ArrayAdapter<QuestionModel>(this, android.R.layout.simple_list_item_1, dbHelper.getAll());
        //tv_maxScore.setText(dbHelper.getAll().toString());


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadPreferences();
    }

    private void initValues(){
        et_nickName = findViewById(R.id.et_nickname);
        button_start = findViewById(R.id.button_start);
        tv_maxScore = findViewById(R.id.textView_maxScore);
        tv_error = findViewById(R.id.textView_error);
        button_settings = findViewById(R.id.button_settings);
        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        tv_error.setTextColor(getResources().getColor(R.color.error));
        loadPreferences();
    }

    private void startGame(){
        String nick = et_nickName.getText().toString();
        tv_error.setText("");
        tv_error.setBackgroundColor(Color.TRANSPARENT);
        if(nick.equals("")){
            tv_error.setText("Tienes que introducir un nombre");
            tv_error.setBackgroundColor(Color.BLACK);
            return;
        }
        if(nick.length()>=10){
            tv_error.setText("El nombre debe tener menos de 10 caracteres");
            tv_error.setBackgroundColor(Color.BLACK);
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nick", nick);
        editor.commit();


        //Launch new Activity
        Intent i = new Intent(this, QuestionsActivity.class);
        startActivity(i);

    }

    public void startSettings(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void loadPreferences(){
        String txt_maxScore = "Puntuaciones maximas:\n";
        for(int i = 1; i <= MAX_SCORES_AMOUNT; i++)
            txt_maxScore += "\n" + i + "- " + preferences.getString("txt_max" + i, "") + " " +  preferences.getString("punt_max" + i, "") + "\n";
        tv_maxScore.setText(txt_maxScore);
        String nick = preferences.getString("nick", "");
        et_nickName.setText(nick);
        int amount = preferences.getInt("numPreguntas", 10);
        int type = preferences.getInt("category", -1);
        QuestionReader.MAX_QUESTIONS = amount;
        QuestionReader.QUESTION_CATEGORY = type;
    }
}
