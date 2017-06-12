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

public class MainPlayer extends AnimatedSprite {

    protected Bitmap bitmap;
    protected int frameNumber;
    protected int frameHeight;
    protected int frameWidth;
    protected int numFrames;
    protected int framesPerRow;
    protected Rect rectToBeDrawn;
    protected ArrayList<Bitmap> asteroids;

    public MainPlayer() {
        super();
    }

    public void updatePosition() {

    }

}

