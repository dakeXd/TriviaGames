package com.example.triviagames;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.triviagames.fragments.ButtonBasedFragment;
import com.example.triviagames.fragments.CheckBoxBasedFragment;
import com.example.triviagames.fragments.ImageButtonBasedFragment;
import com.example.triviagames.fragments.QuestionFragment;
import com.example.triviagames.fragments.RadioButtonBasedFragment;
import com.example.triviagames.fragments.SpinnerBasedFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class QuestionReader {

    private static final int ADDITIONAL_LINES_BY_QUESTION = 2;
    public static final int MAX_QUESTIONS = 10;
    private static int[] asked;                         //Questions index asked until now
    private static int actual_question = -1;
    private static int correct_questions = 0;
    private static Random random;
    private static FragmentManager fragmentManager;
    private static Resources resources;

    /**
     * @return the actual question number.
     */
    public static int getActual_question() {
        return actual_question;
    }

    /**
     * @return the number of correct questions
     */
    public static int getCorrect_questions() {
        return correct_questions;
    }

    /**
     * Restart and initialize the Question Reader variables
     */
    public static void start(FragmentManager fm, Resources r){
        random = new Random();
        asked = new int[MAX_QUESTIONS];
        for(int i = 0; i < MAX_QUESTIONS; i++){
            asked[i] = -1;
        }
        fragmentManager = fm;
        resources = r;
        actual_question = -1;
        correct_questions = 0;
        nextQuestion(false);
    }


    /**
     * Change the actual question fragment, loading a new question from the questions.txt document
     * @param correct is the answer of the last question correct?
     */
    public static void nextQuestion(boolean correct){
        //If the last answer was correct, update correct_questions, in any case, update actual_question
        if(correct)
            correct_questions++;
        actual_question++;
        if(actual_question == MAX_QUESTIONS){
            Activity activity = fragmentManager.findFragmentByTag("FRAGMENT_QUESTION").getActivity();
            Intent i = new Intent(activity, FinalScoreActivity.class);
            activity.startActivity(i);
            activity.finish();
        }else {
            //Create a new fragment, load the questions and change fragment
            FragmentTransaction ft = fragmentManager.beginTransaction();
            QuestionFragment nextFragment = loadQuestion();
            ft.replace(R.id.fragmentContainerView, nextFragment, "FRAGMENT_QUESTION");
            ft.commit();
        }
    }

    /**
     * Load a new question from questions document
     * @return the next fragment
     */
    private static QuestionFragment loadQuestion(){
        try {
            //Load a buffered reader from the questions document
            BufferedReader br = new BufferedReader(new InputStreamReader(resources.openRawResource(R.raw.questions)));

            //The first line of the document have the number of total questions
            String linea = br.readLine();
            int totalQuestions = Integer.parseInt(linea.split("#")[1]);
            //Debug:
            System.out.println("Preguntas encontradas: " + totalQuestions);

            //Try to get a new answer index. If the index was already taken by another question, it will try to get another one.
            int nextQuestionIndex;
            do {
                nextQuestionIndex = random.nextInt(totalQuestions);
            }while (!checkQuestionIndex(nextQuestionIndex));
            asked[actual_question] = nextQuestionIndex;

            //Skip all the lines necessary to reach the next question. (One line by question plus the additional lines by question defined in the constant)
            for(int i = 0; i < nextQuestionIndex*(QuestionFragment.MAX_ANSWERS+ADDITIONAL_LINES_BY_QUESTION); i++)
                br.readLine();



            //Instantiate and return the new fragment
            QuestionFragment nextFragment = createFragment(br);
            br.close();

            return nextFragment;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a question index was already taken
     * @param index the index to check
     * @return if the index is free to choose
     */
    private static boolean checkQuestionIndex(int index){
        for(int i = 0; i< actual_question; i++){
            if(index == asked[i])
                return false;
        }
        return true;
    }

    /**
     * Create a new fragment based in the text document actual line
     * @param br the Buffered Reader reading the document
     * @return the Fragment to load
     */
    private static QuestionFragment createFragment(BufferedReader br){
        try {

            //The first line of the question defines the fragment type
            int type = Integer.parseInt(br.readLine());
            QuestionFragment nextFragment;

            //The fragment variables
            String question;
            String questionImage;
            String questions[] = new String[QuestionFragment.MAX_ANSWERS];
            int answers[] = new int[QuestionFragment.MAX_ANSWERS];

            //Each integer have a different kind of Fragment associated.
            switch (type) {
                case 2:
                    nextFragment = new CheckBoxBasedFragment();
                    break;
                case 3:
                    nextFragment = new ImageButtonBasedFragment();
                    break;
                case 4:
                    nextFragment = new RadioButtonBasedFragment();
                    break;
                case 5:
                    nextFragment = new SpinnerBasedFragment();
                    break;
                default:
                    nextFragment = new ButtonBasedFragment();
                    break;
            }
            //The first line is the question
            String questionArray[] = br.readLine().split("#");
            question =  questionArray[0];
            questionImage = questionArray.length > 1 ? questionArray[1] : null;
            //The next lines have each answer and if that answer is true or false.
            for (int i = 0; i < QuestionFragment.MAX_ANSWERS; i++) {
                String nextLine[] = br.readLine().split("#");
                questions[i] = nextLine[0];
                answers[i] = Integer.parseInt(nextLine[1]);
            }

            //Set the Fragment variables.
            nextFragment.setQuestion(question);
            nextFragment.setQuestionImage(questionImage);
            nextFragment.setAnswers(answers);
            nextFragment.setQuestions(questions);
            nextFragment.setReady(true);

            return nextFragment;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
