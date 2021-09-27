package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int MAX_SCORES_AMOUNT = 5;

    private EditText et_nickName;
    private Button button_start;
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



    }

    private void initValues(){
        et_nickName = findViewById(R.id.et_nickname);
        button_start = findViewById(R.id.button_start);
        tv_maxScore = findViewById(R.id.textView_maxScore);
        tv_error = findViewById(R.id.textView_error);
        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        String nick = preferences.getString("nick", "");
        et_nickName.setText(nick);
        loadMaxScorePlayers();
    }

    private void startGame(){
        String nick = et_nickName.getText().toString();
        if(nick.equals("")){
            tv_error.setText("Tienes que introducir un nombre");
            return;
        }
        if(nick.length()>=10){
            tv_error.setText("El nombre debe tener menos de 10 caracteres");
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nick", nick);
        editor.commit();

    }

    private void loadMaxScorePlayers(){
        String txt_maxScore = "Puntuaciones maximas:\n";
        for(int i = 1; i <= MAX_SCORES_AMOUNT; i++)
            txt_maxScore += "\n" + i + "- " + preferences.getString("txt_max" + i, "") + " " +  preferences.getString("punt_max" + i, "") + "\n";
        tv_maxScore.setText(txt_maxScore);
    }
}
