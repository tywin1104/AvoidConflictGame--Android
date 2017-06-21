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
import android.widget.Toast;

import java.io.IOException;
import java.security.Key;
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
    int ememyHeight = 200;
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
    dPad fireButton;
    Exit exit;

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

        fireButton = new dPad(screenWidth/6, screenHeight/9, screenWidth/6 * 6, screenHeight/9 * 8);

        exit = new Exit(context, screenWidth/6, screenHeight/50, (screenWidth/2 - (screenWidth/12)), 0);


        enemies = new ArrayList<Enemy>();
        enemies = generateEnemy(context);

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

        //Send the bullet in the random horizontal direction
        Random randomNumber = new Random();


        for(int i=0;i<4;i++) {
            Bullet bullet = bullets[i];
            int bulletDirection = randomNumber.nextInt(3);
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
            DestoyBullet();
            // collideWithExit();
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


    public ArrayList<Enemy> generateEnemy(Context context)
    {
        for(int i=0; i < 1; i++)
        {
            int x = x_offset;
            int y = i * (ememyHeight + enemyGapY) + y_offset;

            while(x < screenWidth)
            {
                enemies.add(new Enemy(context, enemyBreadth, ememyHeight, x, y));
                x += enemyBreadth + enemyGapX;
            }

        }

        return enemies;
    }

    public void DestoyBullet () {
        for (int i = 0; i < bullets.length; i++) {
            Bullet bullet = bullets[i];
            if (bullet.getPositionY() > screenHeight - bullet.getWidth() || collision(bullets[i])) {
                boolean isHit = collision((bullets[i]));
                if(isHit) {
                    player.setPositionX(screenWidth / 2);
                    player.setPositionY(screenHeight - 100);
                }

                /**
                 lives = lives - 1;
                 if (lives == 0) {
                 lives = 3;
                 score = 0;

                 }
                 */

                //reset the bullet  to the top of the screen
                bullet.setPositionY(enemies.get(i).getPositionY());
                bullet.setPositionX(enemies.get(i).getPositionX());


                //what horizontal direction should we use
                //for the next falling bullet
                bullet.moveDown();
                Random randomNumber = new Random();


                int bulletDirection = randomNumber.nextInt(3);
                switch (bulletDirection) {

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
    }

    public boolean collideWithExit ()
    {
        if (exit.getPositionY() + exit.getWidth() >= player.getPositionY() && exit.getPositionY() + exit.getWidth() <= player.getPositionY() + player.getHeight() / 2 && exit.getPositionX() + exit.getWidth() >= player.getPositionX() && exit.getPositionX() <= player.getPositionX() + player.getWidth()) {
            return true;
        }
        if (exit.getPositionY() <= player.getPositionY() + player.getHeight() && exit.getPositionY() >= player.getPositionY() + player.getHeight() / 2 && exit.getPositionX() + exit.getWidth() > player.getPositionX() && exit.getPositionX() < player.getPositionX() + player.getWidth()) {
            return true;
        }

        if (exit.getPositionX() + exit.getWidth() >= player.getPositionX() && exit.getPositionX() + exit.getWidth() <= player.getPositionX() + player.getWidth() / 2 && exit.getPositionY() + exit.getHeight() >= player.getPositionY() && exit.getPositionY() <= player.getPositionY() + player.getHeight()) {
            return true;
        }
        if (exit.getPositionX() <= player.getPositionX() + player.getWidth() && exit.getPositionX() > player.getPositionX() + player.getWidth() && exit.getPositionY() + exit.getHeight() >= player.getPositionY() && exit.getPositionY() <= player.getPositionY() + player.getHeight()) {
            return true;
        }
        return false;
    }


    public void updateCourt(Bullet bullet) {

        // move racket only if it is not at the edge of the screen

        if (player.isMovingRight()) {
            if(player.getPositionX() + player.getWidth()< screenWidth){ //move right only if you haven't hit the right edge
                player.updatePosition();
            }
        }

        if (player.isMovingLeft()) {
            if (player.getPositionX() > 0){  //move left only if you haven't hit the left edge
                player.updatePosition();
            }
        }

        if (player.isMovingDown()) {
            if (player.getPositionY() + player.getHeight()/2 < (screenHeight-40)){  //move down only if you haven't hit the bottomedge
                player.updatePosition();
            }
        }

        if (player.isMovingUp()) {
            if (player.getPositionY() > 0){  //move up only if you haven't hit the top edge
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

        bullet.updatePosition();



//        //collison between player and bullets
//        for(int i=0;i<bullets.length;i++) {
//            if(collision(bullets[i]))  {
//                bullets[i].setPositionY(enemies.get(i).getPositionY());
//                bullets[i].setPositionX(enemies.get(i).getPositionX());
//
//            }
//        }
    }


    public boolean  collision(Bullet bullet) {
        if (bullet.getPositionY() + bullet.getWidth() >= player.getPositionY() && bullet.getPositionY() + bullet.getWidth() <= player.getPositionY() + player.getHeight() / 2 && bullet.getPositionX() + bullet.getWidth() >= player.getPositionX() && bullet.getPositionX() <= player.getPositionX() + player.getWidth()) {
            return true;
        }
        if (bullet.getPositionY() <= player.getPositionY() + player.getHeight() && bullet.getPositionY() >= player.getPositionY() + player.getHeight() / 2 && bullet.getPositionX() + bullet.getWidth() > player.getPositionX() && bullet.getPositionX() < player.getPositionX() + player.getWidth()) {
            return true;
        }

        if (bullet.getPositionX() + bullet.getWidth() >= player.getPositionX() && bullet.getPositionX() + bullet.getWidth() <= player.getPositionX() + player.getWidth() / 2 && bullet.getPositionY() + bullet.getHeight() >= player.getPositionY() && bullet.getPositionY() <= player.getPositionY() + player.getHeight()) {
            return true;
        }
        if (bullet.getPositionX() <= player.getPositionX() + player.getWidth() && bullet.getPositionX() > player.getPositionX() + player.getWidth() && bullet.getPositionY() + bullet.getHeight() >= player.getPositionY() && bullet.getPositionY() <= player.getPositionY() + player.getHeight()) {
            return true;
        }
        return false;
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
            fireButton.draw(canvas);

            //draw the exit
            exit.draw(canvas);

            if(collideWithExit()) {

                Paint myPaint = new Paint();
                canvas.drawPaint(myPaint);
                myPaint.setColor(Color.BLACK);
                myPaint.setTextSize(24);
                canvas.drawText("My Text", (screenWidth/2), (screenHeight/2), paint);

            }

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
