package com.strobertchs.cptfinalgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Nathan on 2017-06-19.
 */

public class dPad extends Sprite {


    public dPad(int dPad_width, int dPad_height, int positionX, int positionY)
    {
        super();


        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(dPad_width);
        setHeight(dPad_height);
    }



    //@Override
    public boolean onTouchEvent(MotionEvent motionEvent, int gameWidth, int gameHeight, MainPlayer player) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                //right D-pad
                if (motionEvent.getX() <= gameWidth/2 && motionEvent.getX() >= (gameWidth/6 * 2)) {
                    if (motionEvent.getY() <= (gameHeight - gameHeight/9) && motionEvent.getY() >= (gameHeight/9 * 7)) {
                        player.moveRight();
                    }
                }

                //left D-pad
                if (motionEvent.getX() <= gameWidth/6) {
                    if (motionEvent.getY() <= (gameHeight - gameHeight/9) && motionEvent.getY() >= (gameHeight/9 * 7)) {
                        player.moveLeft();
                    }
                }

                //up D-pad
                if (motionEvent.getX() <= (gameWidth/2 - gameWidth/6) && motionEvent.getX() >= gameWidth/6) {
                    if (motionEvent.getY() <= (gameHeight/9 * 7) && motionEvent.getY() >= (gameHeight/9 * 6)) {
                        player.moveUp();
                    }
                }

                //down D-pad
                if(motionEvent.getX() <= (gameWidth/6 * 2) && motionEvent.getX() >= gameWidth/6) {
                    if (motionEvent.getY() <= gameHeight && motionEvent.getY() >= gameHeight - gameHeight/9) {
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


    public void draw(Canvas source_canvas) {
        // left dpad
        source_canvas.drawRect(getPositionX(),
                getPositionY(),
                getPositionX() - (getWidth()),
                getPositionY() - (getHeight()),
                paint);

        // right dpad
        source_canvas.drawRect(getPositionX(),
                getPositionY(),
                getPositionX() - (getWidth()),
                getPositionY() - (getHeight()),
                paint);

        // up dpad
        source_canvas.drawRect(getPositionX(),
                getPositionY(),
                getPositionX() - (getWidth()),
                getPositionY() - (getHeight()),
                paint);

        // down dpad
        source_canvas.drawRect(getPositionX(),
                getPositionY(),
                getPositionX() - (getWidth()),
                getPositionY() - (getHeight()),
                paint);

    }
}
