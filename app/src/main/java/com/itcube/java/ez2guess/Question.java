package com.itcube.java.ez2guess;


//Хранит картинку, варианты ответов и правильный ответ
public class Question {
    String choice1, choice2, choice3, choice4;
    String answer;
    String picSrc;

    Question(String choice1, String choice2, String choice3, String choice4, String answer, String picSrc){
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.picSrc = picSrc;
    }
}
