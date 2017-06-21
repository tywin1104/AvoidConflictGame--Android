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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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


    int enemyBreadth = 200;
    int ememyHeight = 100;
    int enemyGapX = 100;
    int enemyGapY = 100;
    int y_offset = 100;
    int x_offset = 50;


    ArrayList<Enemy> enemies;
    //ArrayList<Bullet> bullets;
    Bullet[] bullets = new Bullet[4];

    MainPlayer player;
    dPad leftDpad;
    dPad rightDpad;
    dPad upDpad;
    dPad downDpad;
    dPad centreDpad;

    long lastFrameTime;
    int fps;
    int lives;
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

        player = new MainPlayer(context, sScreenHeight, sScreenWidth);

        leftDpad = new dPad(screenWidth/6, screenHeight/9, screenWidth/6, screenHeight/9 * 8);

        rightDpad = new dPad(screenWidth/6, screenHeight/9, screenWidth/6 * 3, screenHeight/9 * 8);

        downDpad = new dPad(screenWidth/6, screenHeight/9, screenWidth/6 * 2, screenHeight/9 * 9);

        upDpad = new dPad(screenWidth/6, screenHeight/9, screenWidth/6 * 2, screenHeight/9 * 7);

        centreDpad = new dPad(screenWidth/6, screenHeight/9, screenWidth/6 * 2, screenHeight/9 * 8);


        enemies = new ArrayList<Enemy>();
        enemies = generateEnemy();

//            for(int j = 0; j < enemies.size(); j ++)
//            {
//                bullets.add(new Bullet(context, screenWidth, screenHeight, enemies.get(j).getPositionX(),enemies.get(j).getPositionY()));
//                bullets.get(j).moveDown();
//
//            }
        int index = 0;
        while(index < 4) {
            bullets[index] = new Bullet(context, screenWidth, screenHeight, enemies.get(index).getPositionX(),enemies.get(index).getPositionY());
            index++;
        }


        for(int i=0;i<4;i++) {
            Bullet temp = bullets[i];
            temp.moveDown();
        }



        lives = 3;
        score = 0;

        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.grass_14);

        //Send the ball in the random horizontal direction
        Random randomNumber = new Random();
        int bulletDirection = randomNumber.nextInt(3);

        for(int i=0;i<4;i++) {
            Bullet bullet = bullets[i];
            switch(bulletDirection){
                case 0:
                    bullet.moveLeft();
                    break;

                case 1:
                    bullet.moveRight();
                    break;

                case 2:
                    bullet.moveStraight();
                    break;
            }
        }


    }

    @Override
    public void run() {
        while (playPeaceGame) {
            for(int i=0;i<4;i++) {
                updateCourt(bullets[i]);
            }
            drawCourt();
            controlFPS();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                //right D-pad
                if (motionEvent.getX() <= screenWidth/2 && motionEvent.getX() >= (screenWidth/6 * 2)) {
                    if (motionEvent.getY() <= (screenHeight - screenHeight/9) && motionEvent.getY() >= (screenHeight/9 * 7)) {
                        player.moveRight();
                    }
                }

                //left D-pad
                if (motionEvent.getX() <= screenWidth/6) {
                    if (motionEvent.getY() <= (screenHeight - screenHeight/9) && motionEvent.getY() >= (screenHeight/9 * 7)) {
                        player.moveLeft();
                    }
                }

                //up D-pad
                if (motionEvent.getX() <= (screenWidth/2 - screenWidth/6) && motionEvent.getX() >= screenWidth/6) {
                    if (motionEvent.getY() <= (screenHeight/9 * 7) && motionEvent.getY() >= (screenHeight/9 * 6)) {
                        player.moveUp();
                    }
                }

                //down D-pad
                if(motionEvent.getX() <= (screenWidth/6 * 2) && motionEvent.getX() >= screenWidth/6) {
                    if (motionEvent.getY() <= screenHeight && motionEvent.getY() >= screenHeight - screenHeight/9) {
                        player.moveDown();
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                player.stop();
                break;
        }
        return true;
    }


    public ArrayList<Enemy> generateEnemy()
    {
        for(int i=0; i < 1; i++)
        {
            int x = x_offset;
            int y = i * (ememyHeight + enemyGapY) + y_offset;

            while(x < screenWidth)
            {
                enemies.add(new Enemy(enemyBreadth, ememyHeight, x, y));
                x += enemyBreadth + enemyGapX;
            }

        }

        return enemies;
    }


    public void updateCourt(Bullet bullet) {

        // move racket only if it is not at the edge of the screen

        if (player.isMovingRight()) {
            if(player.getPositionX() + player.getWidth()/2 < screenWidth){ //move right only if you haven't hit the right edge
                player.updatePosition();
            }
        }

        if (player.isMovingLeft()) {
            if (player.getPositionX() - player.getWidth()/2 > 0){  //move left only if you haven't hit the left edge
                player.updatePosition();
            }
        }

        if (player.isMovingDown()) {
            if (player.getPositionY() - player.getHeight()/2 < (screenHeight-40)){  //move down only if you haven't hit the bottomedge
                player.updatePosition();
            }
        }

        if (player.isMovingUp()) {
            if (player.getPositionY() - player.getHeight()/2 > 0){  //move up only if you haven't hit the top edge
                player.updatePosition();
            }
        }


        //hit right of screen
        if (bullet.getPositionX() + bullet.getWidth() > screenWidth) {
            bullet.moveLeft();
        }


        //hit left of screen
        if (bullet.getPositionX() < 0) {
            bullet.moveRight();
        }

        //Edge of bullet has hit bottom of screen
        if (bullet.getPositionY() > screenHeight - bullet.getWidth())
        {

            /**
             lives = lives - 1;
             if (lives == 0) {
             lives = 3;
             score = 0;

             }
             */

            //reset the ball to the top of the screen
            bullet.setPositionY(400);
            bullet.setPositionX(screenWidth / 2);


            //what horizontal direction should we use
            //for the next falling ball
            Random randomNumber = new Random();

            bullet.moveDown();

            int ballDirection = randomNumber.nextInt(3);
            switch (ballDirection) {

                case 0:
                    bullet.moveLeft();
                    break;
                case 1:
                    bullet.moveRight();
                    break;
                case 2:
                    bullet.moveStraight();
                    break;
            }
        }



        bullet.updatePosition();
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

            for (int i = 0; i < enemies.size(); i ++)
            {
                enemies.get(i).draw(canvas);
            }

            //Draw the main player
            player.draw(canvas);

            //Draw the bullet


            for(int i=0;i<4;i++) {
                bullets[i].draw(canvas);
            }
            //bullet.draw(canvas);

            // draw all the dpad objects
            upDpad.draw(canvas);
            downDpad.draw(canvas);
            rightDpad.draw(canvas);
            leftDpad.draw(canvas);
            centreDpad.draw(canvas);

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
