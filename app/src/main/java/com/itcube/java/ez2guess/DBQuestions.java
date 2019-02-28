package com.itcube.java.ez2guess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBQuestions {
    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "questions";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUIZNAME = "QuizName";
    private static final String COLUMN_CHOICE1 = "Choice1";
    private static final String COLUMN_CHOICE2 = "Choice2";
    private static final String COLUMN_CHOICE3 = "Choice3";
    private static final String COLUMN_CHOICE4 = "Choice4";
    private static final String COLUMN_ANSWER = "Answer";
    private static final String COLUMN_PIC = "Pic";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_QUIZNAME = 1;
    private static final int NUM_COLUMN_CHOICE1 = 2;
    private static final int NUM_COLUMN_CHOICE2 = 3;
    private static final int NUM_COLUMN_CHOICE3 = 4;
    private static final int NUM_COLUMN_CHOICE4 = 5;
    private static final int NUM_COLUMN_ANSWER = 6;
    private static final int NUM_COLUMN_PIC = 7;

    private SQLiteDatabase mDataBase;

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUIZNAME + " TEXT, " +
                    COLUMN_CHOICE1 + " TEXT, " +
                    COLUMN_CHOICE2 + " TEXT, " +
                    COLUMN_CHOICE3 + " TEXT, " +
                    COLUMN_CHOICE4 + " TEXT, " +
                    COLUMN_ANSWER + " TEXT, " +
                    COLUMN_PIC + " TEXT );";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public DBQuestions(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String quizName, String choice1, String choice2, String choice3, String choice4, String answer, String picSrc) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_QUIZNAME, quizName);
        cv.put(COLUMN_CHOICE1, choice1);
        cv.put(COLUMN_CHOICE2, choice2);
        cv.put(COLUMN_CHOICE3, choice3);
        cv.put(COLUMN_CHOICE4, choice4);
        cv.put(COLUMN_ANSWER, answer);
        cv.put(COLUMN_PIC, picSrc);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Question> selectAll(String quizName) {
        String selection = COLUMN_QUIZNAME + " = ?";
        String[] selectionArguments = new String[]{quizName};

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, selection, selectionArguments, null, null, null);

        ArrayList<Question> result = new ArrayList<Question>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                String choice1, choice2, choice3, choice4, answer, picSrc;
                choice1 = mCursor.getString(NUM_COLUMN_CHOICE1);
                choice2 = mCursor.getString(NUM_COLUMN_CHOICE2);
                choice3 = mCursor.getString(NUM_COLUMN_CHOICE3);
                choice4 = mCursor.getString(NUM_COLUMN_CHOICE4);
                answer = mCursor.getString(NUM_COLUMN_ANSWER);
                picSrc = mCursor.getString(NUM_COLUMN_PIC);
                result.add(new Question(choice1, choice2, choice3, choice4, answer, picSrc));
            } while (mCursor.moveToNext());
        }
        return result;
    }


}
