package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

class UpgradeScreen {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int[][] colors = {{255,255,0,0},{255,255,165,0},{255,255,255,0},{255,0,128,0},{255,0,0,255}};
    private float[] starX = new float[50];
    private float[] starY = new float[50];
    private int[] starSize = new int[50];
    private long buyTimer = System.currentTimeMillis();

    UpgradeScreen(){
        for(int a = 0; a < starX.length;a++){
            starX[a] = (float)Math.random()*X(2000);
            starY[a] = (float)Math.random()*Y(1000);
            starSize[a] = (int)(Math.random()*20) + 20;
        }
    }

    void tick() {

    }

    void render(Canvas canvas, Upgrades upgrades, Data data, Bitmap star, Bitmap coin) {
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        for(int a = 0; a < starX.length; a++){
            if(starX[a]+starSize[a]*9/7 > X(600) && starX[a]<X(1400)&&starY[a]+starSize[a]>Y(0)&&starY[a]<Y(250)){
                continue;
            }
            canvas.drawBitmap(Bitmap.createScaledBitmap(star,9*starSize[a]/7,starSize[a],true),starX[a],starY[a],paint);
        }
        paint.setColor(Color.CYAN);
        paint.setTextSize(X(150));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        canvas.drawText("UPGRADES", X(1000), Y(150),paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(5));
        canvas.drawText("UPGRADES", X(1000), Y(150),paint);
        paint.setTextSize(X(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawText(data.getGold(),X(960),Y(260),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,212,175,55);
        canvas.drawText(data.getGold(),X(960),Y(260),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(100),(int)X(100),true),X(960)+ data.getGold().length()*X(28),Y(170),paint);
        for(int a = 0; a < 2; a ++) {
            for (int b = 0; b < 2; b++) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(X(10));
                if((a+b)%2==0){
                    paint.setColor(Color.BLUE);
                }else{
                    paint.setARGB(255,0,128,255);
                }
                canvas.drawArc(X(50) + a * X(1000), Y(300) + b * Y(250), X(150) + a * X(1000), Y(500) + b * Y(250), 90f, 180f, true, paint);
                canvas.drawArc(X(850) + a * X(1000), Y(300) + b * Y(250), X(950) + a * X(1000), Y(500) + b * Y(250), -90f, 180f, true, paint);
                canvas.drawLine(X(100) + a * X(1000), Y(300) + b * Y(250), X(900) + a * X(1000), Y(300) + b * Y(250), paint);
                canvas.drawLine(X(100) + a * X(1000), Y(500) + b * Y(250), X(900) + a * X(1000), Y(500) + b * Y(250), paint);
                if((a+b)%2==0){
                    paint.setARGB(255,0,128,255);
                }else{
                    paint.setColor(Color.BLUE);
                }
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(X(100)+ a*X(1000), Y(300) + b * Y(250), X(900) + a*X(1000), Y(500) + b * Y(250), paint);
                canvas.drawArc(X(50) + a*X(1000), Y(300) + b * Y(250), X(150) + a * X(1000), Y(500) + b * Y(250), 90f, 180f, true, paint);
                canvas.drawArc(X(850) + a*X(1000), Y(300) + b * Y(250), X(950) + a * X(1000), Y(500) + b * Y(250), -90f, 180f, true, paint);
            }
        }
        paint.setARGB(255,212,175,55);
        paint.setTextSize(X(60));
        canvas.drawText("Max Health",X(350),Y(360),paint);
        canvas.drawText("Start Lvl",X(1350),Y(360),paint);
        canvas.drawText("Score Multiplier",X(350),Y(610),paint);
        canvas.drawText("Player Weight",X(1350),Y(610),paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextHealthCost()),X(685),Y(480),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextStartLvlCost()),X(1685),Y(480),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextScoreMultiplierCost()),X(685),Y(730),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextPlayerWeightCost()),X(1685),Y(730),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        paint.setARGB(255,212,175,55);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextHealthCost()),X(685),Y(480),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextStartLvlCost()),X(1685),Y(480),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextScoreMultiplierCost()),X(685),Y(730),paint);
        canvas.drawText(upgrades.simplifyScore(upgrades.getNextPlayerWeightCost()),X(1685),Y(730),paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(X(50));
        paint.setColor(Color.BLUE);
        canvas.drawText("Current: " + upgrades.simplifyScore(upgrades.getHealth()), X(350), Y(420),paint);
        canvas.drawText("Next: " + upgrades.simplifyScore(upgrades.getNextHealth()), X(350), Y(470),paint);
        canvas.drawText("Current: " + upgrades.simplifyScore(Double.toString(upgrades.getPlayerWeight())), X(1350), Y(670),paint);
        canvas.drawText("Next: " + upgrades.simplifyScore(Double.toString(upgrades.getNextPlayerWeight())), X(1350), Y(720),paint);
        paint.setARGB(255,0,128,255);
        canvas.drawText("Current: " + data.getStartLvl(), X(1350), Y(420),paint);
        canvas.drawText("Next: " + (data.getStartLvl()+1), X(1350), Y(470),paint);
        canvas.drawText("Current: " + upgrades.simplifyScore(upgrades.getScoreMultiplier()), X(350), Y(670),paint);
        canvas.drawText("Next: " + upgrades.simplifyScore(upgrades.getNextScoreMultiplier()), X(350), Y(720),paint);

        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(60),(int)X(60),true),X(685)+ upgrades.simplifyScore(upgrades.getNextHealthCost()).length()*X(18),Y(430),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(60),(int)X(60),true),X(685)+ upgrades.simplifyScore(upgrades.getNextScoreMultiplierCost()).length()*X(18),Y(680),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(60),(int)X(60),true),X(1685)+ upgrades.simplifyScore(upgrades.getNextStartLvlCost()).length()*X(18),Y(430),paint);
        canvas.drawBitmap(Bitmap.createScaledBitmap(coin,(int)X(60),(int)X(60),true),X(1685)+ upgrades.simplifyScore(upgrades.getNextPlayerWeightCost()).length()*X(18),Y(680),paint);

        drawUpgradeButton(paint,canvas,X(720),Y(370));
        drawUpgradeButton(paint,canvas,X(1720),Y(370));
        drawUpgradeButton(paint,canvas,X(720),Y(620));
        drawUpgradeButton(paint,canvas,X(1720),Y(620));

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(X(90));
        canvas.drawRect(X(700),Y(825),X(1300),Y(975),paint);
        canvas.drawRect(X(5),Y(5),X(300),Y(140),paint);
        canvas.drawRect(X(1700),Y(5),X(1990),Y(140),paint);
        paint.setARGB(255,212,175,55);
        canvas.drawText("PLAY AGAIN",X(1000),Y(930),paint);
        canvas.drawText("BACK",X(150),Y(100),paint);
        canvas.drawText("GUNS",X(1850),Y(100),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(X(700),Y(825),X(1300),Y(975),paint);
        canvas.drawRect(X(5),Y(5),X(300),Y(140),paint);
        canvas.drawRect(X(1700),Y(5),X(1990),Y(140),paint);
    }

    void touched(MotionEvent e, Data data, Upgrades upgrades, GameScreen gameScreen, GameView gameView, StartScreen startScreen, GunScreen gunScreen, Guns guns) {
        if(e.getX() > X(620) && e.getX()<X(820) && e.getY()>Y(320) && e.getY()<Y(420)&& upgrades.scoreLarger(data.getGold(),upgrades.getNextHealthCost())){
            if(System.currentTimeMillis()-buyTimer > 500) {
                data.setGold(upgrades.subtractScore(data.getGold(), upgrades.getNextHealthCost()));
                data.setMaxHealthLvl(data.getMaxHealthLvl() + 1);
                buyTimer = System.currentTimeMillis();
            }
        }else if(e.getX() > X(1620) && e.getX()<X(1820) && e.getY()>Y(320) && e.getY()<Y(420)&& upgrades.scoreLarger(data.getGold(),upgrades.getNextStartLvlCost())){
            if(System.currentTimeMillis()-buyTimer > 500) {
                data.setGold(upgrades.subtractScore(data.getGold(), upgrades.getNextStartLvlCost()));
                data.setStartLvl(data.getStartLvl() + 1);
                buyTimer = System.currentTimeMillis();
            }
        }else if(e.getX() > X(620) && e.getX()<X(820) && e.getY()>Y(570) && e.getY()<Y(670)&& upgrades.scoreLarger(data.getGold(),upgrades.getNextScoreMultiplierCost())){
            if(System.currentTimeMillis()-buyTimer > 500) {
                data.setGold(upgrades.subtractScore(data.getGold(), upgrades.getNextScoreMultiplierCost()));
                data.setScoreMultiplierLvl(data.getScoreMultiplierLvl() + 1);
                buyTimer = System.currentTimeMillis();
            }
        }else if(e.getX() > X(1620) && e.getX()<X(1820) && e.getY()>Y(570) && e.getY()<Y(670)&& upgrades.scoreLarger(data.getGold(),upgrades.getNextPlayerWeightCost())){
            if(System.currentTimeMillis()-buyTimer > 500) {
                data.setGold(upgrades.subtractScore(data.getGold(), upgrades.getNextPlayerWeightCost()));
                data.setPlayerWeightLvl(data.getPlayerWeightLvl() + 1);
                buyTimer = System.currentTimeMillis();
            }
        }else if(e.getX() > X(700) && e.getX()<X(1300) && e.getY()>Y(825) && e.getY()<Y(975)&& System.currentTimeMillis()-buyTimer > 300){
                gameScreen.reset();
                gameView.setGameState(1);
        }else if(e.getX() > X(0) && e.getX()<X(300) && e.getY()>Y(0) && e.getY()<Y(140)&& System.currentTimeMillis()-buyTimer > 300){
            startScreen.reset();
            gameView.setGameState(0);
        }else if(e.getX() > X(1700) && e.getX()<X(1990) && e.getY()>Y(0) && e.getY()<Y(140)&& System.currentTimeMillis()-buyTimer > 300){
            gunScreen.reset();
            gunScreen.setGunOnScreen(data.getAllGunPurchases().indexOf("2"));
            guns.setGunLvl(data.getAllGunPurchases().indexOf("2"));
            gameView.setGameState(4);
        }
    }

    private float X(float X) {
        return X * width/ 2000f;
    }

    private float Y(float Y) {
        return Y * height/ 1000f;
    }

    private void drawUpgradeButton(Paint paint, Canvas canvas, float x, float y){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x -X(100),y-X(50),x+X(100),y+X(50),paint);
        paint.setARGB(255,212,175,55);
        canvas.drawText("BUY",x,y+Y(20),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        canvas.drawRect(x -X(100),y-X(50),x+X(100),y+X(50),paint);
    }

    void reset(){
        buyTimer = System.currentTimeMillis();
    }

}