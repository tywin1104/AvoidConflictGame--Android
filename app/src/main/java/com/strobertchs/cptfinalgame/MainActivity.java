package com.strobertchs.cptfinalgame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;


public class MainActivity extends Activity {

    // Initiating Important Objects
    GameView playingFrenemies;
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

        // setting up the properties for the game object
        playingFrenemies = new GameView(this, screenWidth, screenHeight);
        setContentView(playingFrenemies);

    }


    @Override
    protected void onStop() {
        super.onStop();
        while (true) {
            playingFrenemies.pause();
            break;
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        playingFrenemies.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playingFrenemies.resume();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            playingFrenemies.pause();
            finish();
            return true;
        }
        return false;
    }

}





