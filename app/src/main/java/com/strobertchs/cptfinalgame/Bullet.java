package com.strobertchs.cptfinalgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
/**
 * Created by supriyamutharasan on 2017-06-12.
 */

public class Bullet extends AnimatedSprite {

    protected Bitmap bitmap;
    protected int frameNumber;
    protected int frameHeight;
    protected int frameWidth;
    protected int numFrames;
    protected int framesPerRow;
    protected Rect rectToBeDrawn;
    protected ArrayList<Bitmap> asteroids;

    public Bullet(Context context, int screen_width, int screen_height,int x, int y) {
        super();

        setWidth(screen_width / 15);
        setHeight(screen_width / 15);
        setPositionX(x);    // default the bulltet to be in the horizontal middle
        setPositionY(y);    // default the bullet to be at specific coordinate

        setHorizontal_amount(25);     // horizontal_amount default to 12 pixels
        setUp_amount(10);             // default up_amount to 10
        setDown_amount(17);            // default down_amount to be 6

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);

        frameHeight = bitmap.getHeight();
        frameWidth = bitmap.getWidth();

    }

    /**
     * turn off horizontal movement flags
     */
    public void moveStraight() {
        setMovingLeft(false);
        setMovingRight(false);
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
     * @param source_canvas the canvas object to draw the bullet on
     */
    public void draw(Canvas source_canvas){

        // compute the rectangle to draw the image to
        Rect destRect = new Rect(getPositionX(),
                getPositionY(),
                getPositionX() + getWidth(),
                getPositionY() + getWidth());

        //System.out.println("destRect: " + destRect.toString());
        //show a single image/
        source_canvas.drawBitmap(bitmap, null, destRect, null);
    }



    /**
     * toString: print current position to the console in the F
     * @return
     */
    public String toString(){
        return "BULLET--> x: " + Integer.toString(getPositionX()) + "  y: " + Integer.toString(getPositionY());
    }


}


