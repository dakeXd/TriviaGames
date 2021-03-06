package com.example.triviagames.fragments;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.triviagames.QuestionReader;
import com.example.triviagames.QuestionsActivity;
import com.example.triviagames.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int TIME_MILIS_BETWEEN_QUESTIONS = 1000;
    public static final int MAX_ANSWERS = 4;

    protected String[] questions;
    protected int[] answers;
    protected boolean ready = false;
    private Timer timer = new Timer();

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    public boolean isCorrect(int[] answers){
        MediaPlayer mp;
        for(int i = 0; i < MAX_ANSWERS; i++){
            if(answers[i]!=this.answers[i]){
                mp = MediaPlayer.create(getContext(), R.raw.incorrect_audio);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mp.release();
                    }
                });
                return false;
            }

        }
        mp = MediaPlayer.create(getContext(), R.raw.correct_audio);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
        return true;
    }

    public void start(){
        ((QuestionsActivity) getActivity()).updateQuestion();
    }

    public void checkAnswer(int[] answers){

    }

    protected void waitForNext(boolean correct){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                QuestionReader.nextQuestion(correct);
            }
        }, TIME_MILIS_BETWEEN_QUESTIONS);
    }


}