package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private final int cant1 = 5;
    private final int cant2 = 10;
    private final int cant3 = 15;

    private final int type1 = 1;
    private final int type2 = 2;
    private final int type3 = 3;
    private final int typeAll = -1;

    private Button cantidad1;
    private Button cantidad2;
    private Button cantidad3;

    private Button tipo1;
    private Button tipo2;
    private Button tipo3;
    private Button tipoTodos;

    private Button buttonBack;

    private Button focusCantidad;
    private Button focusTipo;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initValues();
    }

    private void updateAmount(int amount, Button nuevo){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("numPreguntas", amount);
        editor.commit();

        focusCantidad.getBackground().clearColorFilter();
        nuevo.getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                PorterDuff.Mode.SRC_OVER);
        focusCantidad = nuevo;
    }

    private void updateType(int type, Button nuevo){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("category", type);
        editor.commit();
        focusTipo.getBackground().clearColorFilter();
        nuevo.getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                PorterDuff.Mode.SRC_OVER);
        focusTipo = nuevo;
    }


    private void initValues(){
        cantidad1 = (Button) findViewById(R.id.buttonSettings0);
        cantidad2 = (Button) findViewById(R.id.buttonSettings1);
        cantidad3 = (Button) findViewById(R.id.buttonSettings2);
        tipo1 = (Button) findViewById(R.id.buttonSettings3);
        tipo2 = (Button) findViewById(R.id.buttonSettings4);
        tipo3 = (Button) findViewById(R.id.buttonSettings5);
        tipoTodos = (Button) findViewById(R.id.buttonSettings6);
        buttonBack = (Button) findViewById(R.id.buttonSettingsBack);

        cantidad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAmount(cant1, cantidad1);
            }
        });

        cantidad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAmount(cant2, cantidad2);
            }
        });

        cantidad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAmount(cant3, cantidad3);
            }
        });

        tipo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateType(type1, tipo1);
            }
        });

        tipo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateType(type2, tipo2);
            }
        });

        tipo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateType(type3, tipo3);
            }
        });

        tipoTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateType(typeAll, tipoTodos);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preferences = getSharedPreferences("scores", Context.MODE_PRIVATE);

        if(QuestionReader.QUESTION_CATEGORY == type1)
            focusTipo = tipo1;
        else if(QuestionReader.QUESTION_CATEGORY == type2)
            focusTipo = tipo2;
        else if(QuestionReader.QUESTION_CATEGORY == type3)
            focusTipo = tipo3;
        else
            focusTipo = tipoTodos;

        if(QuestionReader.MAX_QUESTIONS == cant1)
            focusCantidad = cantidad1;
        else if(QuestionReader.MAX_QUESTIONS == cant2)
            focusCantidad = cantidad2;
        else
            focusCantidad = cantidad3;

        focusTipo.getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                PorterDuff.Mode.SRC_OVER);
        focusCantidad.getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                PorterDuff.Mode.SRC_OVER);

    }


}