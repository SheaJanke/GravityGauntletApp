package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class WinScreen {
    private long timer = System.currentTimeMillis();
    void tick(){

    }
    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);
        paint .setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextSize((120));
        canvas.drawText("CONGRATULATIONS", X(1000),Y(300),paint);
        paint.setARGB(255,212,175,55);
        canvas.drawText("YOU WIN!",X(1000),Y(500),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawText("CONGRATULATIONS", X(1000),Y(300),paint);

        //draw reset progress button
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(150,255,0,0);
        paint.setTextSize(X(100));
        canvas.drawRect(X(300),Y(700),X(900),Y(950),paint);
        paint.setARGB(150,0,255,0);
        canvas.drawRect(X(1100),Y(700),X(1700),Y(950),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(15));
        paint.setARGB(255,212,175,55);
        canvas.drawRect(X(300),Y(700),X(900),Y(950),paint);
        canvas.drawRect(X(1100),Y(700),X(1700),Y(950),paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Reset",X(600),Y(800),paint);
        canvas.drawText("Progress",X(600),Y(900),paint);
        canvas.drawText("Continue",X(1400),Y(800),paint);
        canvas.drawText("Playing",X(1400),Y(900),paint);


    }

    void touched(MotionEvent e, GameView gameView, Data data,EndScreen endScreen){
        if(System.currentTimeMillis()-timer>500) {
            if (e.getX() > X(300) && e.getX() < X(900) && e.getY() > Y(700) && e.getY() < Y(950)) {
                gameView.setGameState(0);
                data.reset();
            } else if (e.getX() > X(1100) && e.getX() < X(1700) && e.getY() > Y(700) && e.getY() < Y(950)) {
                endScreen.reset();
                gameView.setGameState(2);
            }
        }
    }

    void reset(){
        timer = System.currentTimeMillis();
    }

    private float X(float X) {
        return X * Resources.getSystem().getDisplayMetrics().widthPixels/ 2000f;
    }

    private float Y(float Y) {
        return Y * Resources.getSystem().getDisplayMetrics().heightPixels/ 1000f;
    }


}
