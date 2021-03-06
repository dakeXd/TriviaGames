package com.example.triviagames.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckBoxBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckBoxBasedFragment extends QuestionFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox[] checkBoxes = new CheckBox[MAX_ANSWERS];
    private Button buttonNext;

    public CheckBoxBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckBoxBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckBoxBasedFragment newInstance(String param1, String param2) {
        CheckBoxBasedFragment fragment = new CheckBoxBasedFragment();
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
        View root = inflater.inflate(R.layout.fragment_check_box_based, container, false);

        checkBoxes[0] = (CheckBox) root.findViewById(R.id.checkBox1);
        checkBoxes[1] = (CheckBox) root.findViewById(R.id.checkBox2);
        checkBoxes[2] = (CheckBox) root.findViewById(R.id.checkBox3);
        checkBoxes[3] = (CheckBox) root.findViewById(R.id.checkBox4);
        buttonNext = (Button) root.findViewById(R.id.button3);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answers[] = new int[MAX_ANSWERS];
                for(int i = 0; i < MAX_ANSWERS; i++){
                    if(checkBoxes[i].isChecked()){
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
        super.start();
        if(ready) {
            for (int i = 0; i < QuestionFragment.MAX_ANSWERS; i++) {
                checkBoxes[i].setText(questions[i]);
            }
        }
    }

    @Override
    public void checkAnswer(int answers[]) {
        for(int i = 0; i < QuestionFragment.MAX_ANSWERS; i++){
            if(answers[i]==1){
                checkBoxes[i].getBackground().setColorFilter(getResources().getColor(R.color.wrongAnswer),
                        PorterDuff.Mode.SRC_OVER);
            }
            if(this.answers[i] == 1){
                checkBoxes[i].getBackground().setColorFilter(getResources().getColor(R.color.correctTint),
                        PorterDuff.Mode.SRC_OVER);
            }
        }
        buttonNext.setOnClickListener(null);
        boolean correct = isCorrect(answers);
        waitForNext(correct);
    }
}