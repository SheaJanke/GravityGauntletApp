package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


public class Player {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Upgrades upgrades;
    private Data data;
    private Bitmap playerImage;
    private float lastTouchedX = X(1000);
    private float lastTouchedY = Y(500);
    private int lastTick = 0;
    private int tickCounter = 0;
    private float x = X(1000);
    private float y = Y(500);
    private double weight;
    private double moveAngle;
    private int size;
    private String maxHealth;
    private String health;
    private long time = System.currentTimeMillis();

    Player(Upgrades upgrades, Data data){
        this.data = data;
        this.upgrades = upgrades;
        this.weight = upgrades.getPlayerWeight();
        this.size = (int)X(100);
        this.health = upgrades.getHealth();
        maxHealth = upgrades.getHealth();
    }
    void tick(){
        tickCounter++;
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x,y,size,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        paint.setARGB(255,212,175,55);
        canvas.drawCircle(x,y,size,paint);
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
    void setX(float x){
        this.x = x;
    }
    void setY(float y){
        this.y = y;
    }
    float getX(){
        return x;
    }
    float getY(){
        return y;
    }
    int getSize(){
        return size;
    }
    String getHealth(){
        return health;
    }
    String getMaxHealth(){
        return maxHealth;
    }
    double getWeight(){
        return  weight;
    }
    double getMoveAngle(){
        return moveAngle;
    }
    void setWeight(double weight){
        this.weight = weight;
    }
    void setHealth(String health){
        this.health = health;
    }

    void touched(MotionEvent e, int index){
        if(tickCounter-lastTick <=1 &&(Math.pow(e.getX(index)-lastTouchedX,2)+Math.pow(e.getY(index)-lastTouchedY,2)<X(10000))){
            x+=(e.getX(index)-lastTouchedX);
            y+=(e.getY(index)-lastTouchedY);
            if(x-size <0){
                x = size;
            }else if(x+size>X(2000)){
                x = X(2000)-size;
            }
            if(y-size < 0){
                y = size;
            }else if(y+size>height){
                y = height-size;
            }
        }
        lastTick = tickCounter;
        lastTouchedX = e.getX(index);
        lastTouchedY = e.getY(index);
    }
    void reset(){
        this.weight = upgrades.getPlayerWeight()*Math.pow(1.08,data.getStartLvl());
        this.health = upgrades.getHealth();
        maxHealth = upgrades.getHealth();
    }


}
