package com.example.triviagames.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpinnerBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpinnerBasedFragment extends QuestionFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spinner;
    private Button buttonNext;
    private TextView tv;

    public SpinnerBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpinnerBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpinnerBasedFragment newInstance(String param1, String param2) {
        SpinnerBasedFragment fragment = new SpinnerBasedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spinner_based, container, false);

        spinner= (Spinner) view.findViewById(R.id.spinner3);
        buttonNext = (Button) view.findViewById(R.id.button);
        tv = (TextView) view.findViewById(R.id.textView3);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answers[] = new int[MAX_ANSWERS];

                for(int i = 0; i < MAX_ANSWERS; i++){
                    answers[i] = 0;
                }
                answers[spinner.getSelectedItemPosition()] = 1;
                checkAnswer(answers);

            }
        });
        start();
        return view;
    }

    @Override
    public void start(){
        if(ready) {
            QuestionsActivity activity;
            activity = (QuestionsActivity) getActivity();
            activity.updateQuestion(question, questionImage);
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_preguntas, questions);
            spinner.setAdapter(adapter);

        }
    }

    @Override
    public void checkAnswer(int answers[]) {
        if(this.answers[spinner.getSelectedItemPosition()]==1){
            spinner.getBackground().setColorFilter(getResources().getColor(R.color.correctTint),
                    PorterDuff.Mode.SRC_OVER);
            tv.setTextColor(getResources().getColor(R.color.correctTint));
            tv.setText("¡Respuesta correcta!");
        }else {
            spinner.getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                    PorterDuff.Mode.SRC_OVER);
            tv.setTextColor(getResources().getColor(R.color.wrongAnswer));
            String respuesta = "";
            for(int i = 0; i < MAX_ANSWERS; i++){
                if(this.answers[i]==1)
                    respuesta=questions[i];
            }
            tv.setText("La respuesta correcta era:   " + respuesta);
        }
        buttonNext.setOnClickListener(null);
        boolean correct = isCorrect(answers);
        waitForNext(correct);
    }
}