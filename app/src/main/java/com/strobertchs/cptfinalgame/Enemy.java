package com.strobertchs.cptfinalgame;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by supriyamutharasan on 2017-06-12.
 */

public class Enemy extends Sprite {

    public Enemy(int enemy_width, int enemy_height, int positionX, int positionY)
    {
        super();

        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(enemy_width);
        setHeight(enemy_height);
    }

    public void draw(Canvas source_canvas) {
        source_canvas.drawRect(getPositionX() - (getWidth() / 2),
                getPositionY() - (getHeight() / 2),
                getPositionX() + (getWidth() / 2),
                getPositionY() + (getHeight() / 2),
                paint);

    }

}

