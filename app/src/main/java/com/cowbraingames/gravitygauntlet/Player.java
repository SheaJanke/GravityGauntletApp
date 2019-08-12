package com.cowbraingames.gravitygauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


public class Player {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Bitmap playerImage;
    private float lastTouchedX = X(1000);
    private float lastTouchedY = Y(500);
    private int lastTick = 0;
    private int tickCounter = 0;
    private float x = X(1000);
    private float y = Y(500);

    public Player(){

    }
    public void tick(){
        tickCounter++;
    }

    public void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x,y,(int)X(100),paint);
    }

    private float X(int X){
        return X * width/2000f;
    }

    private float Y(int Y){
        return Y* height/1000f;
    }

    void touched(MotionEvent e){
        if(tickCounter-lastTick <=1){
            x+=e.getX()-lastTouchedX;
            y+=e.getY()-lastTouchedY;
        }
        lastTick = tickCounter;
        lastTouchedX = e.getX();
        lastTouchedY = e.getY();
    }

}
