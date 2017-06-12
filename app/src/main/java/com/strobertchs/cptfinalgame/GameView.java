package com.strobertchs.cptfinalgame;

/**
 * Created by supriyamutharasan on 2017-06-01.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Supriya, Nathaniel, Tywin on 2017-05-08.
 */


class GameView extends SurfaceView implements Runnable {

    Canvas canvas;
    Thread ourThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playPeaceGame;
    Paint paint;

    long lastFrameTime;
    int fps;
    int score;

    int screenWidth;
    int screenHeight;

    Bitmap bitmap;

    public GameView(Context context, int sScreenWidth, int sScreenHeight) {

        super(context);

        screenWidth = sScreenWidth;
        screenHeight = sScreenHeight;

        ourHolder = getHolder();
        paint = new Paint();

        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.grass);

        score = 0;

    }

    @Override
    public void run() {
        while (playPeaceGame) {
            // updateCourt();    // Deals with Collision etc.
            drawCourt();
            controlFPS();
        }
    }

    public void drawCourt() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            //Paint paint = new Paint();
            canvas.drawColor(Color.BLACK);//the background
            paint.setColor(Color.argb(255, 255, 255, 255));

            canvas.drawBitmap(bitmap, 0, 0, paint);
            paint.setTextSize(45);

            canvas.drawText("Score:" + score + " fps:" + fps, 20, 40, paint);

            ourHolder.unlockCanvasAndPost(canvas);


        }
    }



    public void controlFPS()
    {
        long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
        long timeToSleep = 15 - timeThisFrame;
        if (timeThisFrame > 0)
        {
            fps = (int) (1000 / timeThisFrame);
        }
        if (timeToSleep > 0)
        {
            try
            {
                ourThread.sleep(timeToSleep);
            }
            catch (InterruptedException e) {}
        }
        lastFrameTime = System.currentTimeMillis();

    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                /*
                if (motionEvent.getX() >= screenWidth / 2) {
                    player.moveRight();
                } else {
                    player.moveLeft();
                }

                break;

            case MotionEvent.ACTION_UP:
                player.stop();
                break;
        }
        return true;
    }
    */

    public void pause() {
        playPeaceGame = false;
        try {
            ourThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playPeaceGame = true;
        ourThread = new Thread(this);
        ourThread.start();
    }

}
