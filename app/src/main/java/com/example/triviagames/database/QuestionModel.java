package com.example.triviagames.database;

import com.example.triviagames.fragments.QuestionFragment;

import java.util.Arrays;

public class QuestionModel {

    private int id;

    //1-ButtonBasedQuestion, 2-CheckBoxBasedQuestion(multiOption), 3-ImageButtonBasedQuestion, 4-RadioButtonBasedQuestion, 5-SpinnerBasedQuestion
    private int QuestionType;
    //0-None, 1-Image, 2-Audio, 3-Video
    private int MultimediaType;
    private int QuestionCategory;

    private String question;
    private String multimediaSource;
    private String[] answers;
    private int[] correctAnswers;

    public QuestionModel(int id, int questionType, int multimediaType, int questionCategory, String question, String multimediaSource, String[] answers, int[] correctAnswers) {
        this.id = id;
        QuestionType = questionType;
        MultimediaType = multimediaType;
        QuestionCategory = questionCategory;
        this.question = question;
        this.multimediaSource = multimediaSource;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
    }

    public QuestionModel(){
        answers = new String[QuestionFragment.MAX_ANSWERS];
        correctAnswers = new int[QuestionFragment.MAX_ANSWERS];
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "id=" + id +
                ", QuestionType=" + QuestionType +
                ", MultimediaType=" + MultimediaType +
                ", QuestionCategory=" + QuestionCategory +
                ", question='" + question + '\'' +
                ", multimediaSource='" + multimediaSource + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", correctAnswers=" + Arrays.toString(correctAnswers) +
                '}';
    }

    public int getQuestionCategory() {
        return QuestionCategory;
    }

    public void setQuestionCategory(int questionCategory) {
        QuestionCategory = questionCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(int questionType) {
        QuestionType = questionType;
    }

    public int getMultimediaType() {
        return MultimediaType;
    }

    public void setMultimediaType(int multimediaType) {
        MultimediaType = multimediaType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMultimediaSource() {
        return multimediaSource;
    }

    public void setMultimediaSource(String multimediaSource) {
        this.multimediaSource = multimediaSource;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
