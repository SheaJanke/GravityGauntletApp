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

    Player(int size, Upgrades upgrades, Data data){
        this.data = data;
        this.upgrades = upgrades;
        this.weight = upgrades.getPlayerWeight();
        this.size = size;
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

    void touched(MotionEvent e){
        if(tickCounter-lastTick <=1 &&(Math.pow(e.getX()-lastTouchedX,2)+Math.pow(e.getY()-lastTouchedY,2)<X(10000))){
            /*if(System.currentTimeMillis()-time>50&&(Math.pow(e.getX()-lastTouchedX,2)+Math.pow(e.getY()-lastTouchedY,2)>0.1)) {
                if (e.getX() - lastTouchedX > 0 && e.getY() - lastTouchedY > 0) {
                    moveAngle = Math.atan((e.getY() - lastTouchedY) / (e.getX() - lastTouchedX)) * 180 / Math.PI + 90;
                } else if (e.getX() - lastTouchedX < 0 && e.getY() - lastTouchedY > 0) {
                    moveAngle = Math.atan((e.getY() - lastTouchedY) / (e.getX() - lastTouchedX)) * 180 / Math.PI - 90;
                } else if (e.getX() - lastTouchedX < 0 && e.getY() - lastTouchedY < 0) {
                    moveAngle = Math.atan((e.getY() - lastTouchedY) / (e.getX() - lastTouchedX)) * 180 / Math.PI - 90;
                } else if (e.getX() - lastTouchedX > 0 && e.getY() - lastTouchedY < 0) {
                    moveAngle = Math.atan((e.getY() - lastTouchedY) / (e.getX() - lastTouchedX)) * 180 / Math.PI + 90;
                }
                if(newMoveAngle>moveAngle){
                    if(newMoveAngle-moveAngle<(180-newMoveAngle)+(moveAngle+180)){
                        if(newMoveAngle-moveAngle<10){
                            moveAngle=newMoveAngle;
                        }else{
                            moveAngle+=10;
                        }
                    }else{
                        if((180-newMoveAngle)+(moveAngle+180)< 10){
                            moveAngle = newMoveAngle;
                        }else{
                            moveAngle-=10;
                            if(moveAngle<-180){
                                moveAngle+=360;
                            }
                        }
                    }
                }else{
                    if(moveAngle-newMoveAngle<(180-moveAngle)+(newMoveAngle+180)){
                        if(moveAngle-newMoveAngle<10){
                            moveAngle=newMoveAngle;
                        }else{
                            moveAngle-=10;
                        }
                    }else{
                        if((180-moveAngle)+(newMoveAngle+180)< 10){
                            moveAngle = newMoveAngle;
                        }else{
                            moveAngle+=10;
                            if(moveAngle>180){
                                moveAngle-=360;
                            }
                        }
                    }
                }
                time = System.currentTimeMillis();
            }*/
            x+=e.getX()-lastTouchedX;
            y+=e.getY()-lastTouchedY;
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
        lastTouchedX = e.getX();
        lastTouchedY = e.getY();
    }
    void reset(){
        this.weight = upgrades.getPlayerWeight()*Math.pow(1.1,data.getStartLvl());
        this.health = upgrades.getHealth();
        maxHealth = upgrades.getHealth();
    }


}
