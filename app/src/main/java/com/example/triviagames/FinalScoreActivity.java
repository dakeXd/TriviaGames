package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.triviagames.fragments.QuestionFragment;

import org.w3c.dom.Text;

import java.util.prefs.Preferences;

public class FinalScoreActivity extends AppCompatActivity {

    private TextView tv_punt, tv_maxPunt, tv_message;
    private Button button_back;
    private String nombres[];
    private String puntuaciones[];
    private int int_puntuaciones[];
    SharedPreferences preferences;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        tv_punt = findViewById(R.id.tv_finalScore);
        tv_maxPunt = findViewById(R.id.tv_maxPuntuacion);
        tv_message = findViewById(R.id.tv_message);
        button_back = findViewById(R.id.button_main_menu);
        nombres = new String[MainActivity.MAX_SCORES_AMOUNT];
        puntuaciones = new String[MainActivity.MAX_SCORES_AMOUNT];
        int_puntuaciones = new int[MainActivity.MAX_SCORES_AMOUNT];
        position = -1;
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        loadMaxScores();
        printMaxScores();
        if(position>-1)
            saveNewMasSCores();

    }

    private void loadMaxScores(){

        for(int i = 1; i <= MainActivity.MAX_SCORES_AMOUNT; i++){
            int index = i-1;
            nombres[index] =preferences.getString("txt_max" + i, "");
            puntuaciones[index] = preferences.getString("punt_max" + i, "");
            if(puntuaciones[index].equals("")){
               int_puntuaciones[index] = -1;
            }else{
                int_puntuaciones[index] = Integer.parseInt(puntuaciones[index].split("/")[0]);
            }
            if(QuestionReader.getCorrect_questions()>int_puntuaciones[index] && position<=-1)
                position = index;
        }

        if(position>-1) {
            for (int i = MainActivity.MAX_SCORES_AMOUNT - 1; i > position; i--) {
                nombres[i] = nombres[i-1];
                puntuaciones[i] = puntuaciones[i-1];
                int_puntuaciones[i] = int_puntuaciones[i-1];
            }
            nombres[position] = preferences.getString("nick", "");
            puntuaciones[position] = QuestionReader.getCorrect_questions() + "/" + QuestionReader.MAX_QUESTIONS;
            int_puntuaciones[position] = QuestionReader.getCorrect_questions();
        }
    }

    private void printMaxScores(){
        String txt_maxScore = "Puntuaciones maximas:\n";
        for(int i = 0; i < MainActivity.MAX_SCORES_AMOUNT; i++)
            txt_maxScore += "\n" + (i+1) + "- " + nombres[i] + " " +  puntuaciones[i] + "\n";
        tv_maxPunt.setText(txt_maxScore);
        if(position>-1){
            tv_message.setTextColor(Color.GREEN);
            tv_message.setText("Enhorabuena, has logrado una nueva puntuacion maxima, has quedado en el lugar numero " + (position+1));
        }else{
            tv_message.setTextColor(Color.RED);
            tv_message.setText("Lastima, no has alcanzado una puntuacion maxima, mas suerte la proxima");
        }

        tv_punt.setText("Tu puntuacion ha sido: " + QuestionReader.getCorrect_questions() + "/" + QuestionReader.MAX_QUESTIONS);
    }

    private void saveNewMasSCores(){
        SharedPreferences.Editor editor = preferences.edit();
        for(int i = 1; i <= MainActivity.MAX_SCORES_AMOUNT; i++){
            editor.putString("txt_max" + i, nombres[i-1]);
            editor.putString("punt_max" + i, puntuaciones[i-1]);
        }
        editor.commit();
    }
}