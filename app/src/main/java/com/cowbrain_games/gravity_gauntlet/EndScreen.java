package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class EndScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private long beginTime = System.currentTimeMillis();
    private int[][] colors = {{255,0,0,255},{255,0,128,255},{255,0,0,255},{255,0,128,255}};
    private float[] x = new float[4];
    private float[] y = new float[4];
    private double angle = 0;
    private Bitmap star;
    private Bitmap coin;
    private float[] starX = new float[50];
    private float[] starY = new float[50];
    private int[] starSize = new int[50];
    private boolean[] starIncreasing = new boolean[50];
    private int tickCounter = 0;
    private Data data;
    private GameScreen gameScreen;
    EndScreen(Bitmap star, Bitmap coin, Data data, GameScreen gameScreen){
        this.coin = coin;
        this.data = data;
        this.gameScreen = gameScreen;
        this.star = star;
        for(int a = 0; a < starX.length;a++){
            starX[a] =
                    starY[a] =
                            starSize[a] = (int)(Math.random()*20) + 20;
            starIncreasing[a] = (a<20);
        }
        int a = 0;
        while(a < starX.length){
            float x = (float)Math.random()*X(2000);
            float y = (float)Math.random()*Y(1000);
            boolean tooClose = false;
            for(int b = 0; b < a; b++){
                if(Math.sqrt(Math.pow(starX[b]-x,2) + Math.pow(starY[b] -y,2)) < X(80)){
                    tooClose = true;
                }
            }
            if(!tooClose){
                starX[a] = x;
                starY[a] = y;
                a++;
            }
        }
    }


    void tick(){
        for(int a = 0; a < 4;a++){
            x[a] = X(1000) + X(700)*(float)Math.cos(angle + Math.PI*a/2);
            y[a] = Y(500) + Y(350)*(float)Math.sin(angle + Math.PI*a/2);
        }
        if(tickCounter>2) {
            for (int a = 0; a < starIncreasing.length; a++) {
                if (starIncreasing[a]) {
                    starSize[a] = starSize[a] + 1;
                    if (starSize[a] > 40) {
                        starIncreasing[a] = false;
                    }
                } else {
                    starSize[a] = starSize[a] - 1;
                    if (starSize[a] < 20) {
                        starIncreasing[a] = true;
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
            if(starX[a]+50*9/7f > X(700) && starX[a]<X(1300)&&starY[a]+50>Y(300)&&starY[a]<Y(700)){
                continue;
            }
            canvas.drawBitmap(Bitmap.createScaledBitmap(star,9*starSize[a]/7,starSize[a],true),starX[a]-starSize[a]*9/14f,starY[a]-starSize[a]/2f,paint);
        }
        canvas.drawRect(X(700),Y(350),X(1300),Y(650),paint);
        paint.setColor(Color.RED);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(X(140));
        canvas.drawText("Game Over",X(1000),Y(480),paint);
        paint.setTextSize(X(80));
        paint.setColor(Color.WHITE);
        canvas.drawText("Earned: +" + gameScreen.getGoldEarned(),X(970),Y(560),paint);
        canvas.drawText("Gold: " + data.getGold(),X(970),Y(640),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(80),(int)X(80),true),X(970)+("Earned: +" + gameScreen.getGoldEarned()).length()*X(20),Y(495),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(80),(int)X(80),true),X(970)+("Gold: " + data.getGold()).length()*X(20),Y(575),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        paint.setTextSize(X(140));
        paint.setARGB(255,128,0,0);
        canvas.drawText("Game Over",X(1000),Y(480),paint);
        paint.setTextSize(X(80));
        paint.setARGB(255,212,175,55);
        canvas.drawText("Earned: +" + gameScreen.getGoldEarned(),X(970),Y(560),paint);
        canvas.drawText("Gold: " + data.getGold(),X(970),Y(640),paint);
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
        paint.setTextSize(X(80));
        canvas.drawText("PLAY AGAIN",x[0],y[0]+Y(30),paint);
        canvas.drawText("UPGRADES",x[1],y[1]+Y(30),paint);
        canvas.drawText("GUNS",x[2],y[2]+Y(25),paint);
        canvas.drawText("MENU",x[3],y[3]+Y(25),paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        paint.setTextSize(X(100));
        paint.setTextSize(X(80));
        canvas.drawText("PLAY AGAIN",x[0],y[0]+Y(30),paint);
        canvas.drawText("UPGRADES",x[1],y[1]+Y(30),paint);
        canvas.drawText("GUNS",x[2],y[2]+Y(25),paint);
        canvas.drawText("MENU",x[3],y[3]+Y(25),paint);



    }
    void reset(){
        beginTime = System.currentTimeMillis();
    }

    void touched(MotionEvent e, GameView gameView, GameScreen gameScreen, UpgradeScreen upgradeScreen, GunScreen gunScreen, StartScreen startScreen){
        if(System.currentTimeMillis()-beginTime >500) {
            if (e.getX() > x[0] - X(300) && e.getX() < x[0] + X(300) && e.getY() > y[0] - Y(100) && e.getY() < y[0] + (100)) {
                gameScreen.reset();
                gameView.setGameState(1);
            }else if (e.getX() > x[1] - X(300) && e.getX() < x[1] + X(300) && e.getY() > y[1] - Y(100) && e.getY() < y[1] + (100)) {
                upgradeScreen.reset();
                gameView.setGameState(3);
            }else if (e.getX() > x[2] - X(300) && e.getX() < x[2] + X(300) && e.getY() > y[2] - Y(100) && e.getY() < y[2] + (100)) {
                gunScreen.reset();
                gameView.setGameState(4);
            }else if (e.getX() > x[3] - X(300) && e.getX() < x[3] + X(300) && e.getY() > y[3] - Y(100) && e.getY() < y[3] + (100)) {
                startScreen.reset();
                gameView.setGameState(0);
            }
        }
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
