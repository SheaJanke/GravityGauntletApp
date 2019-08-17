package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class StartScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels+100;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int[][] colors = {{255,255,0,0},{255,255,165,0},{255,255,255,0},{255,0,128,0},{255,0,0,255}};
    private float[] x = new float[4];
    private float[] y = new float[4];
    private double angle = 0;
    private Bitmap star;
    private float[] starX = new float[40];
    private float[] starY = new float[40];
    private int[] starSize = new int[40];
    private boolean[] starIncreaing = new boolean[40];
    private long tickCounter = 0;
    StartScreen(Bitmap star){
        this.star = star;
        for(int a = 0; a < starX.length;a++){
            starX[a] = (float)Math.random()*X(2000);
            starY[a] = (float)Math.random()*Y(1000);
            starSize[a] = (int)(Math.random()*20) + 20;
            if(a<20){
                starIncreaing[a] = true;
            }else{
                starIncreaing[a]= false;
            }
        }
    }


    void tick(){
        for(int a = 0; a < 4;a++){
            x[a] = X(1000) + X(700)*(float)Math.cos(angle + Math.PI*a/2);
            y[a] = Y(500) + Y(350)*(float)Math.sin(angle + Math.PI*a/2);
        }
         if(tickCounter>3) {
             for (int a = 0; a < starIncreaing.length; a++) {
                 if (starIncreaing[a]) {
                     starSize[a] = starSize[a] + 1;
                     if (starSize[a] > 40) {
                         starIncreaing[a] = false;
                     }
                 } else {
                     starSize[a] = starSize[a] - 1;
                     if (starSize[a] < 20) {
                         starIncreaing[a] = true;
                     }
                 }
             }
             tickCounter = 0;
         }
        angle+=0.010;
        tickCounter++;
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        canvas.drawColor(Color.BLACK);
        for(int a = 0; a < starX.length; a++){
            if(starX[a]+40*9/7 > X(700) && starX[a]<X(1300)&&starY[a]+40>Y(350)&&starY[a]<Y(650)){
                continue;
            }
            canvas.drawBitmap(Bitmap.createScaledBitmap(star,9*starSize[a]/7,starSize[a],true),starX[a]-starSize[a]*9/14f,starY[a]-starSize[a]/2f,paint);
        }
        paint.setColor(Color.CYAN);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(140));
        canvas.drawText("Gravity",X(1000),Y(480),paint);
        canvas.drawText("Gauntlet",X(1000),Y(600),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        paint.setColor(Color.BLUE);
        canvas.drawText("Gravity",X(1000),Y(480),paint);
        canvas.drawText("Gauntlet",X(1000),Y(600),paint);
        paint.setStrokeWidth(X(10));
        for(int a = 0;a<4;a++){
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            canvas.drawArc(x[a]+X(200),y[a]-Y(100),x[a]+X(300),y[a]+Y(100),-90f,180f,true,paint);
            canvas.drawArc(x[a]-X(300),y[a]-Y(100),x[a]-X(200),y[a]+Y(100),90f,180f,true,paint);
            canvas.drawLine(x[a]-X(250),y[a]-Y(100),x[a]+X(250),y[a]-Y(100),paint);
            canvas.drawLine(x[a]-X(250),y[a]+Y(100),x[a]+X(250),y[a]+Y(100),paint);
            paint.setARGB(colors[a][0],colors[a][1],colors[a][2],colors[a][3]);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x[a]-X(250),y[a]-Y(100),x[a]+X(250),y[a]+Y(100),paint);
            canvas.drawArc(x[a]+X(200),y[a]-Y(100),x[a]+X(300),y[a]+Y(100),-90f,180f,true,paint);
            canvas.drawArc(x[a]-X(300),y[a]-Y(100),x[a]-X(200),y[a]+Y(100),90f,180f,true,paint);
        }
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setTextSize(X(100));
        canvas.drawText("PLAY",x[0],y[0]+Y(30),paint);
        canvas.drawText("UPGRADES",x[1],y[1]+Y(30),paint);
        paint.setTextSize(X(80));
        canvas.drawText("HOW TO PLAY",x[2],y[2]+Y(25),paint);
        canvas.drawText("COMPETITIVE",x[3],y[3]+Y(25),paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        paint.setTextSize(X(100));
        canvas.drawText("PLAY",x[0],y[0]+Y(30),paint);
        canvas.drawText("UPGRADES",x[1],y[1]+Y(30),paint);
        paint.setTextSize(X(80));
        canvas.drawText("HOW TO PLAY",x[2],y[2]+Y(25),paint);
        canvas.drawText("COMPETITIVE",x[3],y[3]+Y(25),paint);



    }

    void touched(MotionEvent e, GameView gameView){
        if(e.getX() > x[0]-X(300) && e.getX() < x[0]+X(300) && e.getY()> y[0]-Y(100) && e.getY()< y[0]+(100)){
            gameView.setGameState(1);
        }
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
