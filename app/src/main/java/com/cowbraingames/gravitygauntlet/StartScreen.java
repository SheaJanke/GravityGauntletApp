package com.cowbraingames.gravitygauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

class StartScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private double angle = 0;
    private float x1 = X(500);


    void tick(){
        angle -= 0.010;
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.BLUE);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(175));
        canvas.drawText("Gravity",X(500),Y(200),paint);
        canvas.drawText("Gauntlet",X(500),Y(400),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        paint.setColor(Color.WHITE);
        canvas.drawText("Gravity",X(500),Y(200),paint);
        canvas.drawText("Gauntlet",X(500),Y(400),paint);




    }

    private float X(int X){
        return X * width/1000f;
    }

    private float Y(int Y){
        return Y* height/2000f;
    }
}
