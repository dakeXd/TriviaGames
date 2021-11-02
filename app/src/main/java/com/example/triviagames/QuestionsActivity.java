package com.example.triviagames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.triviagames.fragments.ButtonBasedFragment;
import com.example.triviagames.fragments.MultimediaNoneFragment;

public class QuestionsActivity extends AppCompatActivity {

    private TextView tv_question, tv_puntuacion, tv_acertadas;
    //private ImageView iv_question;
    private Button button_back;

    private ConstraintLayout cl;
    private ConstraintSet constraintSet;
    private FragmentContainerView multimediaFragment;
    private FragmentContainerView fragmentQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);



        //iv_question = (ImageView) findViewById(R.id.imageView_pregunta);
        multimediaFragment = (FragmentContainerView) findViewById(R.id.MultimediaFragment);
        //iv_question.setVisibility(View.GONE);
        tv_puntuacion = (TextView) findViewById(R.id.tv_puntuacion);
        tv_acertadas = (TextView) findViewById(R.id.tv_correct);
        button_back = (Button) findViewById(R.id.button_back);
        fragmentQuestion = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
        cl = (ConstraintLayout) findViewById(R.id.ConstraintQuestion);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragmentContainerView, new ButtonBasedFragment(), "FRAGMENT_QUESTION");
        fm.beginTransaction().replace(R.id.MultimediaFragment, new MultimediaNoneFragment(), "FRAGMENT_MULTIMEDIA");
        QuestionReader.start(fm, getResources(), this);
    }

    public void updateQuestion(){
        tv_puntuacion.setText((QuestionReader.getActual_question() + 1) + "/" + QuestionReader.MAX_QUESTIONS);
        tv_acertadas.setText(QuestionReader.getCorrect_questions() + "");
    }






}