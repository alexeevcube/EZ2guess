package com.itcube.java.ez2guess;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    Quiz quiz;
    Button choice1Button, choice2Button, choice3Button, choice4Button;
    ImageView image;
    Toast tmpToast;

    private void quit(){
        this.finish();
    }

    private void setQuestion(){
        //Перемешиваем варианты
        List choice = Arrays.asList(quiz.getCurrentChoice());
        Collections.shuffle(choice);

        //Устанавливаем надписи на кнопках и картинку
        choice1Button.setText((String)choice.get(0));
        choice2Button.setText((String)choice.get(1));
        choice3Button.setText((String)choice.get(2));
        choice4Button.setText((String)choice.get(3));

        image.setImageResource(QuizActivity.this.getResources().getIdentifier(
                quiz.getCurrentPic(),
                "drawable",
                QuizActivity.this.getPackageName()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        quiz = new Quiz(this, "Quiz1");

        choice1Button = (Button) findViewById(R.id.choice1);
        choice2Button = (Button) findViewById(R.id.choice2);
        choice3Button = (Button) findViewById(R.id.choice3);
        choice4Button = (Button) findViewById(R.id.choice4);
        image = (ImageView) findViewById(R.id.image);

        choice1Button.setOnClickListener(this);
        choice2Button.setOnClickListener(this);
        choice3Button.setOnClickListener(this);
        choice4Button.setOnClickListener(this);

        if (quiz.getQuestionCount() == 0){
            //в случае отсутствия вопросов выводим сообщение об ошибке
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка")
                    .setMessage("В базе отсутствуют вопросы")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.presence_busy)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            quit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            setQuestion();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v){
        Button choiceButton = (Button) findViewById(v.getId());
        if (tmpToast != null) {
            tmpToast.cancel();
        }

        //Проверяем на правильность
        if (quiz.getAnswer().equalsIgnoreCase((String) choiceButton.getText())){
            tmpToast = Toast.makeText(this, "ВЕРНО", Toast.LENGTH_SHORT);
            tmpToast.show();
            quiz.increaseScore();
        }else{
            tmpToast = Toast.makeText(this, "НЕ УГАДАЛ", Toast.LENGTH_SHORT);
            tmpToast.show();
        }

        if (quiz.hasNextQuestion()){
            //Переходим на следующий слайд
            quiz.moveCursor();
            setQuestion();
        }else{
            //Выводим результат
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Игра окончена")
                    .setMessage("Результат: " + quiz.getScore() + "/" + quiz.getQuestionCount() + " (" +
                            100*quiz.getScore()/quiz.getQuestionCount() + "%).")
                    .setCancelable(false)
                    .setIcon(android.R.drawable.btn_star_big_on)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            quit();
                            tmpToast.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
