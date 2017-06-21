package com.strobertchs.cptfinalgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;
import android.graphics.Rect;


/**
 * Created by Nathan on 2017-06-21.
 */

public class Exit extends Sprite {

    protected Bitmap bitmap;
    protected int frameNumber;
    protected int frameHeight;
    protected int frameWidth;

    public Exit(Context context, int exit_width, int exit_height, int positionX, int positionY)
    {
        super();

        paint.setColor(Color.argb(255, 255, 0, 0));

        setPositionX(positionX);
        setPositionY(positionY);

        setWidth(exit_width);
        setHeight(exit_height);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.exit);

        frameHeight = bitmap.getHeight();
        frameWidth = bitmap.getWidth();

    }

    public void draw(Canvas source_canvas) {
        Rect destRect = new Rect(getPositionX(),
                getPositionY(),
                getPositionX() + getWidth(),
                getPositionY() + getWidth());

        source_canvas.drawBitmap(bitmap, null, destRect, null);


    }
}
