package com.example.triviagames.fragments;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageButtonBasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageButtonBasedFragment extends QuestionFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageButton buttons[] = new ImageButton[QuestionFragment.MAX_ANSWERS];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ImageButtonBasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageButtonBasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageButtonBasedFragment newInstance(String param1, String param2) {
        ImageButtonBasedFragment fragment = new ImageButtonBasedFragment();
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
        View root = inflater.inflate(R.layout.fragment_image_button_based, container, false);
        buttons[0] = (ImageButton) root.findViewById(R.id.imageButton);
        buttons[1] = (ImageButton) root.findViewById(R.id.imageButton2);
        buttons[2] = (ImageButton) root.findViewById(R.id.imageButton3);
        buttons[3] = (ImageButton) root.findViewById(R.id.imageButton4);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(new int[]{1, 0, 0 , 0});
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(new int[]{0, 1, 0 , 0});
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(new int[]{0, 0, 1 , 0});
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(new int[]{0, 0, 0 , 1});
            }
        });
        start();
        return root;
    }

    @Override
    public void start(){
        if(ready) {
            for (int i = 0; i < QuestionFragment.MAX_ANSWERS; i++) {
                Resources resources = getResources();
                System.out.println(questions[i]);
                Drawable imageId = resources.getDrawable(resources.getIdentifier(questions[i], "drawable", getContext().getPackageName()));
                buttons[i].setImageDrawable(imageId);
            }
        }
    }

    @Override
    public void checkAnswer(int answers[]) {
        for(int i = 0; i < QuestionFragment.MAX_ANSWERS; i++){
            if(answers[i]==1){
                buttons[i].setColorFilter(getResources().getColor(R.color.wrongAnswer), PorterDuff.Mode.SRC_OVER);
            }

            if(this.answers[i] == 1){
                buttons[i].setColorFilter(getResources().getColor(R.color.correctTint), PorterDuff.Mode.SRC_OVER);
            }
            buttons[i].setOnClickListener(null);
        }
        boolean correct = isCorrect(answers);
        waitForNext(correct);
    }
}