package com.example.triviagames.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadioButtonBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioButtonBasedFragment extends QuestionFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioButton[] radioButtons = new RadioButton[MAX_ANSWERS];
    private Button buttonNext;

    public RadioButtonBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RadioButtonBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RadioButtonBasedFragment newInstance(String param1, String param2) {
        RadioButtonBasedFragment fragment = new RadioButtonBasedFragment();
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
        View root = inflater.inflate(R.layout.fragment_radio_button_based, container, false);


        radioButtons[0] = (RadioButton) root.findViewById(R.id.radioButton3);
        radioButtons[1] = (RadioButton) root.findViewById(R.id.radioButton5);
        radioButtons[2] = (RadioButton) root.findViewById(R.id.radioButton6);
        radioButtons[3] = (RadioButton) root.findViewById(R.id.radioButton7);
        buttonNext = (Button) root.findViewById(R.id.button2);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answers[] = new int[MAX_ANSWERS];
                for(int i = 0; i < MAX_ANSWERS; i++){
                    if(radioButtons[i].isChecked()){
                        answers[i] = 1;
                    }else{
                        answers[i] = 0;
                    }
                }
                checkAnswer(answers);
            }
        });
        start();

        return root;
    }

    @Override
    public void start(){
        if(ready) {
            QuestionsActivity activity;
            activity = (QuestionsActivity) getActivity();
            activity.updateQuestion(question, questionImage);
            for (int i = 0; i < QuestionFragment.MAX_ANSWERS; i++) {
                radioButtons[i].setText(questions[i]);
            }
        }
    }

    @Override
    public void checkAnswer(int answers[]) {
        for(int i = 0; i < QuestionFragment.MAX_ANSWERS; i++){
            if(answers[i]==1){
                radioButtons[i].setBackgroundColor(Color.RED);
            }
            if(this.answers[i] == 1){
                radioButtons[i].setBackgroundColor(Color.GREEN);
            }
        }
        buttonNext.setOnClickListener(null);
        boolean correct = isCorrect(answers);
        waitForNext(correct);
    }
}