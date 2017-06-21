package com.strobertchs.cptfinalgame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;


public class MainActivity extends Activity {

    GameView playingPeaceGame;
    Display display;
    Point size;
    int screenWidth;
    int screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        playingPeaceGame = new GameView(this, screenWidth, screenHeight);
        setContentView(playingPeaceGame);

    }


    @Override
    protected void onStop() {
        super.onStop();
        while (true) {
            playingPeaceGame.pause();
            break;
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        playingPeaceGame.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playingPeaceGame.resume();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            playingPeaceGame.pause();
            finish();
            return true;
        }
        return false;
    }

}





