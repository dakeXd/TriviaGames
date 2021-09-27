package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int MAX_SCORES_AMOUNT = 5;
    private EditText et_nickName;
    private Button button_start;
    private TextView tv_maxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nickName = findViewById(R.id.et_nickname);
        button_start = findViewById(R.id.button_start);
        tv_maxScore = findViewById(R.id.textView_maxScore);


    }

    private void startGame(){

    }
}
