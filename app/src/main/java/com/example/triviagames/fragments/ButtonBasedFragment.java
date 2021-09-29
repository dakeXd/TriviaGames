package com.example.triviagames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonBasedFragment extends QuestionFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button buttons[] = new Button[QuestionFragment.MAX_ANSWERS];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ButtonBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonBasedFragment newInstance(String param1, String param2) {
        ButtonBasedFragment fragment = new ButtonBasedFragment();
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
        View root = inflater.inflate(R.layout.fragment_button_based, container, false);
        buttons[0] = (Button) root.findViewById(R.id.button_answer1);
        buttons[1] = (Button) root.findViewById(R.id.button_answer2);
        buttons[2] = (Button) root.findViewById(R.id.button_answer3);
        buttons[3] = (Button) root.findViewById(R.id.button_answer4);


        System.out.println(buttons[0].getText().toString());
        System.out.println(buttons[1].getText().toString());
        System.out.println(buttons[2].getText().toString());
        System.out.println(buttons[3].getText().toString());

        start();
        return root;
    }

    @Override
    public void start(){
        if(ready) {
            System.out.println("START");
            //tv_question.setText(question);
            QuestionsActivity activity = (QuestionsActivity) getActivity();
            activity.updateQuestion(question, questionImage);
            for (int i = 0; i < QuestionFragment.MAX_ANSWERS; i++) {
                buttons[i].setText(questions[i]);
            }
        }
    }
}