package com.itcube.java.ez2guess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button quitButton, startButton, aboutButton;
    MediaPlayer mPlayer; //проигрыватель для фоновой музыки
    ImageButton soundButton; //кнопка вкл/выкл звука
    boolean playSound; //флаг звука

    //выход из приложения
    public void quit(){
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //кнопка старта
        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        //Кнопка "Об авторе"
        aboutButton = (Button) findViewById(R.id.about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        // кнопка выхода
        quitButton = (Button) findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
            }
        });

        soundButton = (ImageButton) findViewById(R.id.soundButton);
        //по клику на кнопку звука вкл/выкл фоновую музыку, меняем иконку
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playSound){
                    playSound = false;
                    mPlayer.pause();
                    soundButton.setImageResource(android.R.drawable.ic_lock_silent_mode);
                }else{
                    playSound = true;
                    mPlayer.start();
                    soundButton.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
                }
            }
        });

        //запускаем фоновую музыку
        playSound = true;
        mPlayer = MediaPlayer.create(this, R.raw.music);
        mPlayer.start();
        mPlayer.setLooping(true);//непрерывно

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();//освобождаем проигрыватель
    }
}
