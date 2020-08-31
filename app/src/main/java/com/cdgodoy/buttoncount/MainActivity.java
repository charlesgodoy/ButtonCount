package com.cdgodoy.buttoncount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView tallyCounter;
    Button tallyUpButton;
    Button tallyResetButton;
    int tally = 0;
    int currentTally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tallyCounter = findViewById(R.id.main_tally_textview);
        tallyUpButton = findViewById(R.id.main_tally_up_button);
        tallyResetButton = findViewById(R.id.main_tally_reset_button);

        tallyUpButton.setVisibility(View.VISIBLE);
        tallyUpButton.setBackgroundColor(Color.TRANSPARENT);
        tallyResetButton.setVisibility(View.VISIBLE);
        tallyResetButton.setBackgroundColor(Color.TRANSPARENT);
        tallyCounter.setText(String.valueOf(tally));

        tallyUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tallyUp();
            }
        });

        tallyResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTally != 0) {
                    tally = 0;
                    currentTally = 0;
                    MediaPlayer resetSound = MediaPlayer.create(MainActivity.this, R.raw.button_confirm_spacey);
                    resetSound.start();
                    tallyCounter.setText(String.valueOf(currentTally));
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            tallyUp();
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            tallyDown();
                return true;
            }
        return super.onKeyUp(keyCode, event);
    }

    public void tallyUp() {
        currentTally = ++tally;
        MediaPlayer clicked = MediaPlayer.create(MainActivity.this, R.raw.click_2);
        clicked.start();
        tallyCounter.setText(String.valueOf(currentTally));
//        Log.d("CAZ", "Volume Up was pressed once: " + currentTally);
    }

    public void tallyDown() {
        if (currentTally != 0) {
            currentTally = --tally;
            MediaPlayer clicked = MediaPlayer.create(MainActivity.this, R.raw.click_2);
            clicked.start();
            tallyCounter.setText(String.valueOf(currentTally));
//            Log.d("CAZ", "Volume Down was pressed once: " + currentTally);
        }
    }
}