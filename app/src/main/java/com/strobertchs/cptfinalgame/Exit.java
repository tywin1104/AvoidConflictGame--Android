package com.strobertchs.cptfinalgame;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by Nathan on 2017-06-21.
 */

public class Exit extends Sprite {

    public Exit(int exit_width, int exit_height, int positionX, int positionY)
    {
        super();

        paint.setColor(Color.argb(255, 255, 0, 0));

        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(exit_width);
        setHeight(exit_height);
    }

    public void draw(Canvas source_canvas) {
        source_canvas.drawRect(getPositionX(),
                getPositionY(),
                getPositionX() + (getWidth()),
                getPositionY() + (getHeight()),
                paint);

    }
}
