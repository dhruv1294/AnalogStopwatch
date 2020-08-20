package com.example.analogstopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton playButton,resetButton;
    ClockView clockView;
    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate=0L;
    public static int sec,min,milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.floatingplayButton);
        resetButton = findViewById(R.id.floatingResetButton);
        handler = new Handler();
        clockView = findViewById(R.id.clockView);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);

                    isResume=true;
                    resetButton.setVisibility(View.INVISIBLE);
                    playButton.setImageResource(R.drawable.ic_pause_black_24dp);

                }else{
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);

                    isResume=false;
                    resetButton.setVisibility(View.VISIBLE);
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){

                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    tMilliSec = 0L;
                    tStart =0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec=0;
                    min=0;
                    milliSec=0;


                }
            }
        });

    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int)(tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int)(tUpdate%1000);

            handler.postDelayed(this,60);

        }
    };
}
