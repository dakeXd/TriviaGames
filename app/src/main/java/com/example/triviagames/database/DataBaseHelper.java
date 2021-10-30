package com.example.triviagames.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.triviagames.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final int DB_Version = 1;
    public static final String DB_Name = "questions.db";

    //SQLITE FIELDS

    public static final String QUESTIONS_TABLE = "QUESTIONS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUESTION = "QUESTION";
    public static final String COLUMN_QUESTION_TYPE = COLUMN_QUESTION + "_TYPE";
    public static final String COLUMN_MULTIMEDIA_TYPE = "MULTIMEDIA_TYPE";
    public static final String COLUMN_QUESTION_CATEGORY = "QUESTION_CATEGORY";
    public static final String COLUMN_MULTIMEDIA_SOURCE = "MULTIMEDIA_SOURCE";
    public static final String COLUMN_ANSWERS = "ANSWERS";
    public static final String COLUMN_CORRECT_ANSWERS = "CORRECT_ANSWERS";



    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    //Called the first time the database is accessed, create a new database. If the db is already created, this wont be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("CREATE DATABASE");
        String createStatement = "CREATE TABLE " + QUESTIONS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION_TYPE + " INTEGER NOT NULL, " + COLUMN_MULTIMEDIA_TYPE + " INTEGER NOT NULL, " + COLUMN_QUESTION_CATEGORY + " INTEGER NOT NULL, " + COLUMN_QUESTION + " TEXT NOT NULL, " + COLUMN_MULTIMEDIA_SOURCE + " TEXT,  " + COLUMN_ANSWERS + " TEXT NOT NULL, " + COLUMN_CORRECT_ANSWERS + " TEXT NOT NULL)";
        db.execSQL(createStatement);
        loadDatabase(db);
    }

    //Called if the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void loadDatabase(SQLiteDatabase db){
        System.out.println("LOADING DATABASE");
        addOne(db, new QuestionModel(-1, 1, 0, 1, "Im a question", null, new String[]{"Question1", "Question2", "Question3", "Question4"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 1, 0, 1, "Im question 2", null, new String[]{"2Question1", "2Question2", "2Question3", "2Question4"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 0, 1, "Im a question 3", null, new String[]{"3Question1", "3Question2", "3Question3", "3Question4"}, new int[]{0,0,1,0}));
        System.out.println("FINALIZED LOADING DATABASE");
    }

    public boolean addOne(SQLiteDatabase db, QuestionModel questionModel){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION, questionModel.getQuestion());
        cv.put(COLUMN_QUESTION_TYPE, questionModel.getQuestionType());
        cv.put(COLUMN_MULTIMEDIA_TYPE, questionModel.getMultimediaType());
        cv.put(COLUMN_MULTIMEDIA_SOURCE, questionModel.getMultimediaSource());
        String answer = "";
        String[] answers = questionModel.getAnswers();
        for(int i = 0; i < answers.length;i++){
            answer += (answers[i] + "\n");
        }
        cv.put(COLUMN_ANSWERS, answer);
        String c_answer = "";
        int[] c_answers = questionModel.getCorrectAnswers();
        for(int i = 0; i < c_answers.length;i++){
            c_answer += (c_answers[i] + "\n");
        }
        cv.put(COLUMN_CORRECT_ANSWERS, c_answer);

        long insert = db.insert(QUESTIONS_TABLE, null , cv);
        if(insert == -1)
            return false;
        return true;
    }

    public List<QuestionModel> getAll(){
        String queryString = "SELECT * FROM " + QUESTIONS_TABLE;
        return getQuestionsBySQL(queryString);
    }

    public List<QuestionModel> getQuestionsOfCategory(int category){
        String queryString = "SELECT * FROM " + QUESTIONS_TABLE + " WHERE " + COLUMN_QUESTION_CATEGORY + " = " + category;
        return getQuestionsBySQL(queryString);
    }

    public List<QuestionModel> getQuestionsBySQL(String query){
        List<QuestionModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //if true then there is elements
        if(cursor.moveToFirst()){
            //loop throught the results and create new objects
            do{
                int questionID = cursor.getInt(0);
                int questionType = cursor.getInt(1);
                int multimediaType = cursor.getInt(2);
                int questionCategory = cursor.getInt(3);
                String question = cursor.getString(4);
                String multimediaSource = cursor.getString(5);
                String[] answers = cursor.getString(6).split("\n");
                String[] correctAnswersString = cursor.getString(7).split("\n");
                int[] correctAnswers = new int[correctAnswersString.length];
                for(int i = 0; i< correctAnswers.length; i++){
                    correctAnswers[i] = Integer.parseInt(correctAnswersString[i]);
                }

                QuestionModel newQuestion = new QuestionModel(questionID, questionType, multimediaType, questionCategory, question, multimediaSource, answers, correctAnswers);
                returnList.add(newQuestion);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

}
