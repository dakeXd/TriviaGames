package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triviagames.fragments.ButtonBasedFragment;
import com.example.triviagames.fragments.QuestionFragment;

public class QuestionsActivity extends AppCompatActivity {

    private TextView tv_question, tv_puntuacion;
    private ImageView iv_question;
    private Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);



        tv_question = (TextView) findViewById(R.id.tv_question);
        iv_question = (ImageView) findViewById(R.id.imageView_pregunta);
        iv_question.setVisibility(View.GONE);
        tv_puntuacion = (TextView) findViewById(R.id.tv_puntuacion);
        button_back = (Button) findViewById(R.id.button_back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainerView, new ButtonBasedFragment(), "FRAGMENT_QUESTION");
        QuestionReader.start(fm, getResources());
    }

    public void updateQuestion(String question, String questionImage){
        tv_puntuacion.setText((QuestionReader.getActual_question() + 1) + "/" + QuestionReader.MAX_QUESTIONS);
        tv_question.setText(question);
        if(questionImage==null){
            iv_question.setVisibility(View.GONE);
        }else{
            iv_question.setVisibility(View.VISIBLE);
            Resources resources = getResources();
            Drawable imageId = resources.getDrawable(resources.getIdentifier(questionImage, "drawable", getPackageName()));
            iv_question.setImageDrawable(imageId);
        }

    }




}