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


    public boolean addOne(SQLiteDatabase db, QuestionModel questionModel){
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_QUESTION, questionModel.getQuestion());
        cv.put(COLUMN_QUESTION_TYPE, questionModel.getQuestionType());
        cv.put(COLUMN_MULTIMEDIA_TYPE, questionModel.getMultimediaType());
        cv.put(COLUMN_MULTIMEDIA_SOURCE, questionModel.getMultimediaSource());
        cv.put(COLUMN_QUESTION_CATEGORY, questionModel.getQuestionCategory());
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
            //loop through the results and create new objects
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

    private void loadDatabase(SQLiteDatabase db){
        System.out.println("LOADING DATABASE");
        //MultimediaType --> 0 ninguno, 1 image, 2 audio, 3 video

        //Categoria Noob --> 15 preguntas // 2 audio // 1 video
        addOne(db, new QuestionModel(-1, 1, 0, 1, "¿Como se llama el protagonista del videojuego The Legend of Zelda?", null, new String[]{"Link", "Zelda", "Ganondorf", "Mario"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 2, 0, 1, "¿Cuales de los siguientes son videojuegos de accion?", null, new String[]{"Call of Duty", "Candy Crush", "Zoo Tycoon", "Mortal Kombat"}, new int[]{1,0,0,1}));
        addOne(db, new QuestionModel(-1, 3, 0, 1, "¿Cual de las siguientes imagenes es el logo de Minecraft?", null, new String[]{"minecraft_logo", "mario_logo", "terraria_logo", "factorio_logo"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 3, 0, 1, "Selecciona el juego que salio con mayor anterioridad al mercado", null, new String[]{"minecraft_logo", "tetris_logo", "super_mario_logo", "bomberman_logo"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 5, 0, 1, "¿Cual es el color principal de Waluigi en la saga Super Mario Bros?", null, new String[]{"Rojo", "Verde", "Amarillo", "Morado"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿Cuanto tiempo se tenía para terminar Principe de Persia?", null, new String[]{"120 minutos", "40 minutos", "30 minutos", "60 minutos"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 5, 0, 1, "¿Que instrumento de viento suele usar Link de The Legend of Zelda?", null, new String[]{"Flauta", "Ocarina", "Saxofón", "Armonica"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿De que personaje busca venganza Kratos en God of war?", null, new String[]{"Atenea", "Zeus", "Ares", "Poseidon"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿Como se llama el protagonista de Halo?", null, new String[]{"John", "Soldado Maestro", "Jefe Maestro", "Comandante Total"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿Cuando fue lanzado Super Mario 64?", null, new String[]{"1995", "1998", "1999", "1996"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿De que personaje busca venganza Kratos en God of war?", null, new String[]{"Atenea", "Zeus", "Ares", "Poseidon"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 1, "¿De que personaje ha luchado contra el Caballero Negro y el Genio de la Lampara?", null, new String[]{"Alex Mercer", "Sonic", "Tails", "Pit"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 1, "¿De que videojuego es la siguiente tema?","kirby_audio", new String[]{"Doom", "Super Mario Bros", "Kirby", "Mother"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 3, 2, 1, "¿Que personaje habla?", "ness_audio", new String[]{"ness_image", "lucas_image", "shulk_image", "littlemac_image"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 1, 3, 1, "¿De que videojuego es esta intro?", "hk_video", new String[]{"Follow Knight", "Hollow Knight", "Souls Knight", "Dark Knight"}, new int[]{0,1,0,0}));

        //Categoria Gamer

        addOne(db, new QuestionModel(-1, 1, 1, 2, "¿Cual es el nombre del siguiente personaje?", "barretwallace_foto", new String[]{"Boris Lockhart", "Travis Gainsborough", "Barret Wallace", "John Warten"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 2, 0, 2, "¿Con cual de estos juegos no aprenderias nada de historia?", null, new String[]{"Age of Empires", "Ryse: Son of Rome", "The Witcher 3", "Soul Blade"}, new int[]{0,0,1,1}));
        addOne(db, new QuestionModel(-1, 1, 0, 2, "No hay opciones. Nada salvo una linea recta. ¿En que juego se dice esta frase?", null, new String[]{"Assasins Creed", "Super Mario World", "Max Payne", "Killer Instinc"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 1, 2, "¿Como se llama la institución mental que sirve de escenario a Outlast?", "mountmassive_foto", new String[]{"Mount Massive", "Hospital Estatal de Filadelfia", "Hospital Severalls", "Hospital Whittingham"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 3, 0, 2, "¿Quien es el hijo adoptado de Heihachi de Tekken?", null, new String[]{"liuhachi_foto", "kazuya_foto", "kazumi_foto", "lars_foto"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 3, 0, 3, "¿Cual de estos juegos se ha estrenado en 2021?", null, new String[]{"deadoralive6_foto", "darksiders3_foto", "pillarsoftheeternity_foto", "biomutant_foto"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 2, 0, 2, "¿Cuales de estos personajes son parte del Escuadron E?", null, new String[]{"Ragnarok", "Raz", "Minerva Victor", "Claude Wallace"}, new int[]{0,1,0,1}));
        addOne(db, new QuestionModel(-1, 1, 0, 2, "¿Como se llama el pueblo donde Joel y Ellie viven en The Last of Us 2?", null, new String[]{"Jackdoor", "Watson", "Jackson", "Marston"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 2, "Quien dijo: No elegimos como empezamos en esta vida. La verdadera grandeza es que hacemos con lo que nos toca. ", null, new String[]{"Spyro", "Victor Sullivan", "Ezio Auditore", "Geralt de Rivia"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 0, 2, "Quien dijo: Un hombre elige, un esclavo obedece.", null, new String[]{"Sander Cohen", "John Marston", "Andrew Ryan", "John Warten"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 0, 2, "Quien dijo: La guerra es donde los jóvenes y estúpidos son engañados por los viejos para matarse entre ellos.", null, new String[]{"Boris Lockhart", "William Bishop", "Thomas Hildern", "Niko Bellic"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 5, 0, 2, "Quien dijo: El hombre adecuado en el sitio equivocado puede cambiar el rumbo del mundo", null, new String[]{"Master Chief", "G-Man", "Big Boss", "Big Daddy"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 2, "En que videojuego se escucha este sonido:", "ds_audio", new String[]{"Biosock", "Nino ku ni", "Dark Souls", "Hollow Knight"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 5, 2, 2, "En que videojuego se escucha este sonido:", "sh_audio", new String[]{"Resident Evil", "Silent Hill", "Metal Gear Solid", "Gears of Wars"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 3, 2, "¿De que videojuego es esta intro", "tlou_video", new String[]{"Resident Evil", "Dead Space", "The Last of Us", "Left 4 Dead"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 2, "¿De que personaje es este tema?", "megalovania_audio", new String[]{"Sans", "Chara", "Undyne", "Flowey"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 2, "¿De que personaje es este tema?", "redvsgold_audio", new String[]{"Red vs Gold", "Red vs Mewtwo", "Rayquaza", "Giratina"}, new int[]{1,0,0,0}));

        //Categoria Legendaria

        addOne(db, new QuestionModel(-1, 1, 1, 3, "¿Cual es el nombre del siguiente personaje?", "barretwallace_foto", new String[]{"Boris Lockhart", "Travis Gainsborough", "Barret Wallace", "John Warten"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 1, 3, "¿Cual es el nombre del siguiente personaje?", "travistouchdown_foto", new String[]{"Carl Jeager", "Travis Touchdown", "Scoot Shelby", "Andrew Morton"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 3, 0, 3, "¿Cual de estos juegos se ha estrenado en 2021?", null, new String[]{"deadoralive6_foto", "darksiders3_foto", "pillarsoftheeternity_foto", "biomutant_foto"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 1, 1, 3, "¿Como se llama el fundador de Nintendo?", "fuyasiro_foto", new String[]{"Shigeru Miyamoto", "Satoru Iwata", "Hideo Kojima", "Fusajiro Yamauchi"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 4, 1, 3, "¿Cual es el nombre del siguiente personaje?", "semioscuro_image", new String[]{"Sakura Kasugano", "Nagito Komaeda", "Demifiend", "Akira Nishikiyama"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 1, 3, "¿Cual es el nombre del siguiente personaje?", "mannycalavera_image", new String[]{"Santiago Calavera", "Manny Calavera", "Larry Calavera", "Manuel Calavera"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 4, 1, 3, "¿Cual es el nombre del siguiente personaje?", "guybrush_image", new String[]{"Guybrush Threepwood", "Galbrush Threepwood", "Gilbresg Threewoods", "Guybrush Threewodds"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 1, 1, 3, "¿Cual es el nombre del siguiente personaje?", "corvo_image", new String[]{"Vero Moray", "Corvo Attano", "Gilbresg Threewoods", "Anton Sokolov"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 0, 3, "¿Cual de estos personajes estaba oculto en el Mortal Kombat Original?", null, new String[]{"Ermac", "Rain", "Smoke", "Reptile"}, new int[]{0,0,0,1}));
        addOne(db, new QuestionModel(-1, 1, 0, 3, "¿Cual fue el primer videojuego en presentar el idioma Simlish?", null, new String[]{"SimCopter", "The Sims", "SimCity 3000", "SimCity"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 4, 0, 3, "¿Cual fue la primer consola con CD's?", null, new String[]{"PlayStation", "Sega CD", "3DO", "CD Boy"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 5, 0, 3, "¿Que edicion de Final Fantasy se lanzo en Play Station 1 primero?", null, new String[]{"Final Fantasy VI", "Final Fantasy VII", "Final Fantasy VIII", "Final Fantasy V"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 3, "¿De que videojuego es este tema?", "takeover_audio", new String[]{"Persona 3", "Ninguna de las otras es correcta", "Persona 4", "Persona 5"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 3, "¿En que videojuego suena al final es este tema?", "xenoblade_audio", new String[]{"Xenoblade Chronicles 2", "Xenoblade Chronicles", "Nino ku ni", "Uncharted 4"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 3, 2, 3, "¿En que videojuego suena es este tema?", "botw_audio", new String[]{"majoramask_image", "botw_image", "ocarinaoftime_image", "mariosesentaycuatro_image"}, new int[]{0,1,0,0}));
        addOne(db, new QuestionModel(-1, 5, 2, 3, "¿En que videojuego suena es este tema?", "acnl_audio", new String[]{"Animal Crossing", "Harvest Moon", "Stardew Valley", "Kirby"}, new int[]{1,0,0,0}));
        addOne(db, new QuestionModel(-1, 1, 2, 3, "¿En que videojuego suena es este tema?", "mariokart_audio", new String[]{"Team Sonic Racing", "Diddy Kong Racing", "Mario Kart 8", "Crash Team Racing"}, new int[]{0,0,1,0}));
        addOne(db, new QuestionModel(-1, 1, 3, 3, "¿De que videojuego es es esta secuencia?", "halo_video", new String[]{"Halo 3", "Lego Stars Wars", "Star Fox", "Halo 1"}, new int[]{0,0,0,1}));




        System.out.println("FINALIZED LOADING DATABASE");
    }


}
