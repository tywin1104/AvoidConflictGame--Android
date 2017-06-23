package com.strobertchs.cptfinalgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;



/**
 * Created by supriyamutharasan on 2017-06-12.
 */

public class MainPlayer extends AnimatedSprite
{

    protected Bitmap bitmap;
    protected int frameNumber;
    protected int frameHeight;
    protected int frameWidth;
    protected int numFrames;
    protected int framesPerRow;
    protected Rect rectToBeDrawn;
    private int score = 60;

    /**
     * Constructor method sets up properties for user to play the mainplayer with.
     * @param context
     * @param screen_height
     * @param screen_width
     */
    public MainPlayer(Context context, int screen_height, int screen_width)
    {
        super();

        setWidth(screen_width / 10);
        setHeight(screen_width / 10);

        setPositionX(30);    // default the player to be in the horizontal left side
        setPositionY(screen_height - 100);    // default the player to be at specific coordinate

        setHorizontal_amount(12);     // horizontal_amount default to 12 pixels
        setUp_amount(10);             // default up_amount to 10
        setDown_amount(6);            // default down_amount to be 6

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player123);

        frameHeight = bitmap.getHeight();
        frameWidth = bitmap.getWidth();

    }

    /**
     * gets the score of the player. Used for safety reasons
     * @return player's score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * modifies player's score
     * @param newScore
     */
    public void setScore(int newScore)
    {
        score = newScore;
    }
    /**
     * turn off horizontal movement flags
     */
    public void moveStraight()
    {
        setMovingLeft(false);
        setMovingRight(false);
    }

    /**
     * stop horizontal movement
     */
    public void stop()
    {
        setMovingLeft(false);
        setMovingRight(false);
        setMovingUp(false);
        setMovingDown(false);
    }

    /**
     * updatePosition: based on movement flags, move update the position x and y by the proper movement amount
     */
    public void updatePosition() {

        if (isMovingLeft()) {
            setPositionX(getPositionX() - getHorizontal_amount());
        }

        if (isMovingRight()) {
            setPositionX(getPositionX() + getHorizontal_amount());
        }

        if (isMovingDown()) {
            setPositionY(getPositionY() + getDown_amount());

        }
        if (isMovingUp()) {
            setPositionY(getPositionY() - getUp_amount());

        }

        //advance the frame count
        frameNumber++;

    }

    /**
     * draw: draws the bullet to the canvas object
     * @param source_canvas the canvas object to draw the player on
     */
    public void draw(Canvas source_canvas){

        // compute the rectangle to draw the image to
        Rect destRect = new Rect(getPositionX(),
                getPositionY(),
                getPositionX() + getWidth(),
                getPositionY() + getWidth());

        //System.out.println("destRect: " + destRect.toString());
        source_canvas.drawBitmap(bitmap, null, destRect, null);

    }



    /**
     * toString: print current position to the console in the F
     * @return
     */
    public String toString(){
        return "PLAYER--> x: " + Integer.toString(getPositionX()) + "  y: " + Integer.toString(getPositionY());
    }

}

