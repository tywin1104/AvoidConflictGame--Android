package com.strobertchs.cptfinalgame;

import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by supriyamutharasan on 2017-06-12.
 */

public class Enemy extends Sprite {

    protected Bitmap bitmap;
    protected int frameNumber;
    protected int frameHeight;
    protected int frameWidth;

    public Enemy(Context context, int enemy_width, int enemy_height, int positionX, int positionY)
    {
        super();

        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(enemy_width);
        setHeight(enemy_width);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        frameHeight = bitmap.getHeight();
        frameWidth = bitmap.getWidth();

    }

    public void draw(Canvas source_canvas) {

        Rect desRect = new Rect(getPositionX() - (getWidth() / 2),
                getPositionY() - (getHeight() / 2),
                getPositionX() + (getWidth() / 2),
                getPositionY() + (getHeight() / 2));

        //System.out.println("destRect: " + destRect.toString());
        source_canvas.drawBitmap(bitmap, null, desRect, null);


    }

}

