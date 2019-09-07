package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;

public class Meteor {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Upgrades upgrades;
    private int[][] meteorColor = {{255,0,0},{255,128,0},{255,255,0},{0,255,0},{0,255,255},{0,128,255},{0,0,255},{127,0,225},{255,0,255},{128,128,128}};
    private int lvl;
    private int startPosition = (int)(Math.random()*4);
    private float x;
    private float y;
    private double velX = 0;
    private double velY = 0;
    private int size;
    private String health;

    Meteor(int size, Upgrades upgrades, int lvl){
        this.size = size;
        this.upgrades = upgrades;
        this.lvl = lvl;
        health = upgrades.multiplyScore(upgrades.getMeteorHealth()[lvl],(double)size/X(50));
        if(startPosition == 1){
            x = X((float)Math.random()*2000);
            y = -Y(100);
        }else if(startPosition == 2){
            x = X(2100);
            y = Y((float)Math.random()*1000);
        }else if(startPosition == 3){
            x = X((float)Math.random()*2000);
            y = Y(1100);
        }else{
            x = -X(100);
            y = Y((float)Math.random()*1000);
        }
    }
    void tick(Player player, LinkedList<Meteor> others, GameScreen gameScreen, LinkedList<Bullets> black_holes, int pull){
        //determines the velocity of the meteor based on its position relative to the player
        if(player.getX() == x && player.getY() == y){
            return;
        }
        double radius = Math.sqrt(Math.pow(player.getX()-x, 2) + Math.pow(player.getY()-y, 2));
        double accel = player.getWeight()/(radius * size);
        double accelX = accel * ((player.getX()-x)/radius);
        double accelY = accel * ((player.getY()-y)/radius);
        for(Bullets black_hole:black_holes){
            radius = Math.sqrt(Math.pow(black_hole.getX()-x, 2) + Math.pow(black_hole.getY()-y, 2));
            accel = pull/(radius * size);
            accelX += accel * ((black_hole.getX()-x)/radius);
            accelY += accel * ((black_hole.getY()-y)/radius);
        }
        velX += accelX;
        velY += accelY;
        if((x<X(0)+size && velX < 0)||(x > X(2000)-size && velX > 0)){
            velX = -velX/1.25;
            velY = velY/1.25;
        }
        if((y<Y(0)+size && velY < 0)||(y > Y(1000)-size && velY > 0)){
            velY = -velY/1.25;
            velX = velX/1.25;
        }
        x+= velX;
        y+= velY;

        if(distanceFromPlayer(player) < (size + player.getSize())){
            if(upgrades.scoreLarger(player.getHealth(), "0")){
                player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.35,lvl)*10)));
            }
            gameScreen.removeMeteor(this);
            return;
        }

        //collision detection between meteors
        for(Meteor other : others){
            if(other.getX() != x && other.getY() != y){
                if(squareDistanceFromMeteor(other) < Math.pow(size + other.getSize(),2)){
                    if(x > other.getX() && velX < 0){
                        velX = -velX/1.25;
                    }
                    if(x < other.getX() && velX > 0){
                        velX = -velX/1.25;
                    }
                    if(y > other.getY() && velY < 0){
                        velY = -velY/1.25;
                    }
                    if(y < other.getY() && velY > 0){
                        velY = -velY/1.25;
                    }
                }
            }
        }
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setARGB(255, meteorColor[lvl/10][0], meteorColor[lvl/10][1], meteorColor[lvl/10][2]);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(15 ));
        paint.setColor(Color.WHITE);
        canvas.drawArc(x-size,y-size,x+size,y+size,0, 360,true,paint);
        paint.setARGB(255, meteorColor[lvl/10][0], meteorColor[lvl/10][1], meteorColor[lvl/10][2]);
        canvas.drawArc(x-size,y-size,x+size,y+size,-90, (float)(360*upgrades.divideScores(health,upgrades.multiplyScore(upgrades.getMeteorHealth()[lvl],(double)size/X(50)))),true,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, meteorColor[lvl%10][0], meteorColor[lvl%10][1], meteorColor[lvl%10][2]);
        canvas.drawCircle(x,y,size,paint);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        canvas.drawText(lvl + "",x,y+size/3f,paint);
        paint.setARGB(255,212,178,55);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        canvas.drawText(lvl + "",x,y+size/3f,paint);
    }
    private double distanceFromPlayer(Player player){
        double sumX = player.getX() - getX();
        double sumY = player.getY() - getY();
        return Math.sqrt(Math.pow(sumX, 2) + Math.pow(sumY, 2));
    }

    private double squareDistanceFromMeteor(Meteor meteor){
        double sumX = meteor.getX() - this.getX();
        double sumY = meteor.getY() - this.getY();
        return Math.pow(sumX, 2) + Math.pow(sumY, 2);
    }

    public int getLvl(){
        return lvl;
    }

    float getX(){
        return x;
    }
    float getY(){
        return y;
    }

    int getSize() {
        return size;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }

    void setHealth(String health){
        this.health = health;
    }
    String getHealth(){
        return health;
    }
}
