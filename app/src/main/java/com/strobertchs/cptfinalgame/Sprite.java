package com.strobertchs.cptfinalgame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by eric on 2017-05-08.
 */
public abstract class Sprite {

    private int width;
    private int height;
    private Point position;
    protected Paint paint;


    public Sprite(){

        position = new Point();
        paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255)); // set the default colour to be white

    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPositionX(int newX){
        position.x = newX;
    }

    public void setPositionY(int newY){
        position.y = newY;
    }

    public int getPositionX(){
        return position.x;
    }

    public int getPositionY(){
        return position.y;
    }


    /**
     * Abstract method for drawing the gamepiece to the screen
     * @param canvas the source canvas to draw on
     */
    public abstract void draw(Canvas canvas);



}


