package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.LinkedList;

public class Meteor {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels+100;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Upgrades upgrades;
    private int[][] meteorColor = {{255,0,0},{255,128,0},{255,255,0},{0,255,0},{0,255,255},{0,128,255},{0,0,255},{127,0,225},{255,0,255},{128,128,128}};
    private int lvl;
    private float x = X((float)Math.random()*2000);
    private float y = Y((float)Math.random()*1000);
    private double velX = 0;
    private double velY = 0;
    private int size;

    Meteor(int size, Upgrades upgrades, int lvl){
        this.size = size;
        this.upgrades = upgrades;
        this.lvl = lvl;
    }
    void tick(Player player, LinkedList<Meteor> others, GameScreen gameScreen){
        //determines the velocity of the meteor based on its position relative to the player
        if(player.getX() == x && player.getY() == y){
            return;
        }
        double radius = Math.sqrt(Math.pow(player.getX()-x, 2) + Math.pow(player.getY()-y, 2));
        double accel = player.getWeight()/(radius * size);
        double accelX = accel * ((player.getX()-x)/radius);
        double accelY = accel * ((player.getY()-y)/radius);
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
                player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.6,lvl)*10)));
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
        paint.setARGB(255, meteorColor[lvl%10][0], meteorColor[lvl%10][1], meteorColor[lvl%10][2]);
        canvas.drawCircle(x,y,size,paint);
        paint.setARGB(255, meteorColor[lvl/10][0], meteorColor[lvl/10][1], meteorColor[lvl/10][2]);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(3));
        canvas.drawCircle(x,y,size,paint);
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

    private float getX(){
        return x;
    }
    private float getY(){
        return y;
    }

    private int getSize() {
        return size;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
