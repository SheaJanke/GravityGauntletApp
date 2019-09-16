package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class PauseScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    void tick(){

    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextSize(X(150));
        paint.setARGB(200,0,128,255);
        canvas.drawRect(X(500),Y(100),X(1500),Y(900),paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("PAUSED",X(1000),Y(250),paint);
        canvas.drawRect(X(700),Y(700),X(1300),Y(850),paint);
        paint.setARGB(255,212,175,55);
        paint.setTextSize(X(100));
        canvas.drawText("CONTINUE",X(1000),Y(800),paint);
        paint.setTextSize(X(60));
        canvas.drawText("Move Sensitivity",X(1000),Y(325),paint);
        canvas.drawText("Shoot Button",X(1000),Y(500),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(20));
        canvas.drawRect(X(500),Y(100),X(1500),Y(900),paint);
        paint.setStrokeWidth(X(5));
        canvas.drawRect(X(700),Y(700),X(1300),Y(850),paint);
        paint.setTextSize(X(150));
        canvas.drawText("PAUSED",X(1000),Y(250),paint);

        //move sensitivity bar
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(X(700),Y(390),X(1300),Y(410),paint);

        float x = X(1000);

        paint.setColor(Color.BLUE);
        canvas.drawRect(x-X(15), Y(370),x+X(15), Y(430),paint);
        paint.setARGB(255,212,175,55);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x-X(15), Y(370),x+X(15), Y(430),paint);


    }


    void touched(MotionEvent e){

    }

    float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }

}
