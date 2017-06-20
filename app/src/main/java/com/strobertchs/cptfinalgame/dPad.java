package com.strobertchs.cptfinalgame;

import android.graphics.Canvas;

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

    public void draw(Canvas source_canvas) {
        //source_canvas.drawRect()

    }
}
