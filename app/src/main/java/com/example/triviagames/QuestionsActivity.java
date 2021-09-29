package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triviagames.fragments.ButtonBasedFragment;
import com.example.triviagames.fragments.QuestionFragment;

public class QuestionsActivity extends AppCompatActivity {

    private TextView tv_question;
    private ImageView iv_question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);



        tv_question = (TextView) findViewById(R.id.tv_question);
        iv_question = (ImageView) findViewById(R.id.imageView_pregunta);
        iv_question.setVisibility(View.GONE);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainerView, new ButtonBasedFragment(), "FRAGMENT_QUESTION");
        QuestionReader.start(fm, getResources());
    }

    public void updateQuestion(String question, String questionImage){
        tv_question.setText(question);
        if(questionImage==null){
            iv_question.setScaleX(0);
            iv_question.setScaleY(0);
            iv_question.setVisibility(View.GONE);
        }else{
            iv_question.setVisibility(View.VISIBLE);
            Resources resources = getResources();
            iv_question.setImageDrawable(resources.getDrawable(resources.getIdentifier(questionImage, "drawable", getPackageName())));
        }

    }
}