package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar seekBar;
    ImageView egg;
    ImageView brokenEgg;
    boolean counterIsActive =false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("Go!");
        counterIsActive=false;
        //egg.animate().alpha(1).setDuration(10000);
        //brokenEgg.animate().alpha(0).setDuration(10000);
    }

    public void buttonPressed(View view){

         egg = findViewById(R.id.egg);
         brokenEgg= findViewById(R.id.brokenEgg);

         goButton =findViewById(R.id.button);
        egg.animate().alpha(1);
        brokenEgg.animate().alpha(0);

         if(counterIsActive){
                resetTimer();
         }else {
             counterIsActive =true;
             seekBar.setEnabled(false);
             goButton.setText("STOP!");


             countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {

                 @Override
                 public void onTick(long millisUntilFinished) {
                     updateTimer((int) millisUntilFinished / 1000);
                 }

                 @Override
                 public void onFinish() {
                     Log.d("Sam", "Timer Stopped!");

                     egg.animate().alpha(0);
                     brokenEgg.animate().alpha(1);

                     MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.crackegg);
                     mediaPlayer.start();

                     resetTimer();
                 }
             }.start();
         }
    }



    public void updateTimer(int progress){
        int minutes = progress/60;
        int seconds = progress-(minutes*60);
        String secondsString;

        if(seconds<=9) {
            secondsString = "0"+Integer.toString(seconds);
        }else{
            secondsString = Integer.toString(seconds);
        }

        timerTextView.setText(Integer.toString(minutes)+":"+secondsString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView =findViewById(R.id.timerTextView);

        seekBar =findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
