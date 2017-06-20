package com.strobertchs.cptfinalgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

/**
 * Created by Nathan on 2017-06-19.
 */

public class dPad extends Sprite {


    public dPad(int dPad_width, int dPad_height, int positionX, int positionY)
    {
        super();

        paint.setColor(Color.argb(63, 105, 105, 105));

        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(dPad_width);
        setHeight(dPad_height);
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
