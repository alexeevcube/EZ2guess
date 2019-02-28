package com.itcube.java.ez2guess;

import android.content.Context;

import java.util.ArrayList;

public class Quiz {
    private int score = 0;//счет
    private int questionCount;//количество вопросов в тесте
    private int cursor = 0;//текущий вопрос
    DBQuestions mDBConnector;
    ArrayList<Question> questions;
    String quizName;

    //Создаем игру, загружаем вопросы
    Quiz(Context context, String quizName){
        this.quizName = quizName;
        mDBConnector = new DBQuestions(context);
        fillDB();
        questions = mDBConnector.selectAll(quizName);
        questionCount = questions.size();
    }

    //Наполнение БД
    private void fillDB(){
        mDBConnector.deleteAll();
        mDBConnector.insert("Quiz1", "Гослинг", "Кнут", "Страуструп", "Торвальдс", "Гослинг", "quiz1gosl");
        mDBConnector.insert("Quiz1", "Гослинг", "Кнут", "Страуструп", "Торвальдс", "Кнут", "quiz1knut");
        mDBConnector.insert("Quiz1", "Гослинг", "Кнут", "Страуструп", "Торвальдс", "Страуструп", "quiz1stra");
        mDBConnector.insert("Quiz1", "Гослинг", "Кнут", "Страуструп", "Торвальдс", "Торвальдс", "quiz1torv");
    }

    //Возвращает текущий слайд с вариантами
    public Question getQuestion(int index){
        return questions.get(index);
    }

    //Возвращает варианты ответов текущего слайда
    public String[] getCurrentChoice(){
        return new String[]{getQuestion(cursor).choice1, getQuestion(cursor).choice2,
                getQuestion(cursor).choice3, getQuestion(cursor).choice4};
    }

    //Возвращает имя картинки текущего слайда
    public String getCurrentPic(){
        return getQuestion(cursor).picSrc;
    }

    //Возвращает количество правильных ответов
    public int getScore(){
        return score;
    }

    //Инкримент правильных ответов
    public void increaseScore(){
        score++;
    }

    //Возвращает количество вопросов в текущей игре
    public int getQuestionCount(){
        return questionCount;
    }

    //Возвращает правильный ответ
    public String getAnswer(){
        return getQuestion(cursor).answer;
    }

    //Проверка на наличие следующего вопроса
    public boolean hasNextQuestion(){
        return (questionCount > (cursor + 1));
    }

    //Передвигаем курсор на следующий слайд
    public void moveCursor(){
        if (hasNextQuestion()){
            cursor++;
        }
    }
}
