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
    private float x;
    private int tickCounter = 0;

    PauseScreen(Data data){
        x = X(700) + (data.getSensitivity()-0.5f)*X(300);
    }



    void tick(){
        tickCounter++;
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
        canvas.drawText("CONTINUE",X(1000),Y(810),paint);
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
        paint.setColor(Color.BLUE);
        canvas.drawRect(x-X(15), Y(370),x+X(15), Y(430),paint);
        paint.setARGB(255,212,175,55);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x-X(15), Y(370),x+X(15), Y(430),paint);

        //shoot button position
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        canvas.drawRect(X(1000),Y(530), X(1400),Y(660),paint);
        paint.setColor(Color.RED);
        canvas.drawRect(X(600),Y(530), X(1000),Y(660),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        paint.setStrokeWidth(X(15));
        canvas.drawRect(X(1000),Y(530), X(1400),Y(660),paint);
        canvas.drawRect(X(600),Y(530), X(1000),Y(660),paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(90));
        paint.setColor(Color.WHITE);
        canvas.drawText("LEFT",X(800),Y(630),paint);
        canvas.drawText("RIGHT",X(1200),Y(630),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        paint.setStrokeWidth(X(3));
        canvas.drawText("LEFT",X(800),Y(625),paint);
        canvas.drawText("RIGHT",X(1200),Y(625),paint);



    }


    void touched(MotionEvent e, GameView gameView, Data data){
        if(e.getX()>X(700) && e.getX()<X(1300) && e.getY()>Y(700) && e.getY()<Y(850)){
            gameView.setGameState(1);
        }
        if(e.getX()>x-X(50) && e.getX()<x+X(50) && e.getY()>Y(350) && e.getY()<Y(450)){
            x=e.getX();
            if(x<X(700)){
                x = X(700);
            }else if(x>X(1300)){
                x = X(1300);
            }
            data.setSensitivity((x-X(700))/X(300) + 0.5f);
        }
    }

    float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }

}
