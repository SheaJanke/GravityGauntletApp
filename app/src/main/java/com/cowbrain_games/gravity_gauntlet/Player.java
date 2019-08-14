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
    private Bitmap playerImage;
    private float lastTouchedX = X(1000);
    private float lastTouchedY = Y(500);
    private int lastTick = 0;
    private int tickCounter = 0;
    private float x = X(1000);
    private float y = Y(500);
    private double weight;
    private int size;
    private String maxHealth = "10";
    private String health;

    public Player(double weight, int size, String health){
        this.weight = weight;
        this.size = size;
        this.health = health;
    }
    public void tick(){
        tickCounter++;
    }

    public void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x,y,size,paint);
    }

    private float X(int X){
        return X * width/2000f;
    }

    private float Y(int Y){
        return Y* height/1000f;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }
    public int getSize(){
        return size;
    }
    public String getHealth(){
        return health;
    }
    public String getMaxHealth(){
        return maxHealth;
    }
    public void setMaxHealth(String maxHealth){
        this.maxHealth = maxHealth;
    }
    public void setHealth(String health){
        this.health = health;
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
