package com.cdgodoy.buttoncount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout clMain;
    TextView tallyCounter;
    Button tallyUpButton;
    Button tallyResetButton;
    int tally = 0;
    int currentTally = 0;

    private CountDownTimer mTimer;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clMain = findViewById(R.id.cl_main);
        tallyCounter = findViewById(R.id.main_tally_textview);
        tallyUpButton = findViewById(R.id.main_tally_up_button);
        tallyResetButton = findViewById(R.id.main_tally_reset_button);

        tallyCounter.setText(String.valueOf(tally));

        tallyUpButton.setOnClickListener(view -> tallyUp());

        tallyResetButton.setOnClickListener(view -> {
            if (currentTally != 0) {
                tally = 0;
                currentTally = 0;
                playSound(R.raw.button_confirm_spacey);
                tallyCounter.setText(String.valueOf(currentTally));
                Log.d("time", "Reset button was clicked");

            }
            runTimer();
        });
        runTimer();

        clMain.setOnClickListener(v -> {
            runTimer();
        });
    }


    private void runTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("time", millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                Log.d("time", "timer is finished");

                ScreenSaverDialog.OnDialogDismissListener listener = () -> {
                    Log.d("time", "dialog dismissed!");
                    runTimer();
                };

                FragmentManager fm = getSupportFragmentManager();
                ScreenSaverDialog dialogFragment = new ScreenSaverDialog(listener);
                dialogFragment.show(fm, "screen_saver");
                fm.executePendingTransactions();
               /*
                dialogFragment.getDialog().setOnDismissListener(dialog -> {
                    dialog.dismiss();

                });*/
                //mTimer.cancel();
            }
        };
        mTimer.start();
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
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back();
            return  true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void tallyUp() {
        currentTally = ++tally;
        playSound(R.raw.click_2);
        tallyCounter.setText(String.valueOf(currentTally));
        runTimer();
    }

    public void tallyDown() {
        if (currentTally != 0) {
            currentTally = --tally;
            playSound(R.raw.click_2);
            tallyCounter.setText(String.valueOf(currentTally));
            runTimer();
        }
    }

    private void playSound(int raw) {
        MediaPlayer.create(MainActivity.this, raw).start();
    }

   private void back(){
       if (doubleBackToExitPressedOnce) {
           if (mTimer != null) {
               mTimer.cancel();
           }
           startActivity(new Intent(this, MainMenu.class));
           finish();
           return;
       }else{
           runTimer();
       }

       doubleBackToExitPressedOnce = true;
       Snackbar.make(clMain, "Press back again", Snackbar.LENGTH_SHORT).show();

       new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
   }
}