package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.LinkedList;

class Meteor {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Upgrades upgrades;
    private int[][] meteorColor = {{255,0,0},{255,128,0},{255,255,0},{0,255,0},{0,255,255},{0,128,255},{0,0,255},{127,0,225},{255,0,255},{128,128,128}};
    private int lvl;
    private float x;
    private float y;
    private double velX = 0;
    private double velY = 0;
    private int size;
    private String health;
    private String maxHealth;

    Meteor(int size, Upgrades upgrades, int lvl){
        this.size = size;
        this.upgrades = upgrades;
        this.lvl = lvl;
        int startPosition = (int)(Math.random()*4);
        maxHealth = upgrades.multiplyScore(upgrades.getMeteorHealth()[lvl],(double)size/X(60));
        if(size>=X(100))
            maxHealth = upgrades.multiplyScore(maxHealth,2.0);
        health = maxHealth;
        if(startPosition == 1){
            x = X((float)Math.random()*2000);
            y = -size;
        }else if(startPosition == 2){
            x = X(2000) + size;
            y = Y((float)Math.random()*1000);
        }else if(startPosition == 3){
            x = X((float)Math.random()*2000);
            y = Y(1000)+size;
        }else{
            x = -size;
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
            hitPlayer(player,upgrades,gameScreen);
        }

        //collision detection between meteors
        for(Meteor other : others){
            if(other.getX() != x && other.getY() != y){
                if(squareDistanceFromMeteor(other) < Math.pow(size + other.getSize(),2)){
                    hitMeteor(other);
                }
            }
        }
    }

    void render(Canvas canvas, float strokeWidth){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.RED);
        canvas.drawArc(x-size+strokeWidth/2f,y-size+strokeWidth/2f,x+size-strokeWidth/2f,y+size-strokeWidth/2f,0, 360,true,paint);
        setOutlineColor(paint);
        canvas.drawArc(x-size+strokeWidth/2f,y-size+strokeWidth/2f,x+size-strokeWidth/2f,y+size-strokeWidth/2f,-90, (float)(360*upgrades.divideScores(health,maxHealth)),true,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, meteorColor[lvl%10][0], meteorColor[lvl%10][1], meteorColor[lvl%10][2]);
        canvas.drawCircle(x,y,size-strokeWidth/2f,paint);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);
        setTextColor(paint);
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

    int getLvl(){
        return lvl;
    }

    float getX(){
        return x;
    }
    float getY(){
        return y;
    }

    private double getVelX(){
        return velX;
    }

    private double getVelY(){
        return velY;
    }


    void setX(float x){
        this.x = x;
    }

    void setY(float y){
        this.y = y;
    }

    void setRandomVel(){
        velX = Math.random() * X(20)-X(10);
        velY = Math.random() * X(20)-X(10);
    }

    int getSize() {
        return size;
    }

    float X(float X){
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

    String getMaxHealth(){
        return maxHealth;
    }

    void hitMeteor(Meteor other){
        if(other.getSize()<X(100)) {
            if ((x > other.getX() && velX < 0)||(x < other.getX() && velX > 0)) {
                velX = -velX / 1.25;
            }
            if ((y > other.getY() && velY < 0)||(y < other.getY() && velY > 0)) {
                velY = -velY / 1.25;
            }
        }else{
            double distance = Math.sqrt(Math.pow(x-other.getX(),2) + Math.pow(y-other.getY(),2));
            if ((x > other.getX() && velX < 0)||(x < other.getX() && velX > 0)) {
                velX = other.getVelX() - (velX * Math.abs(x-other.getX())/distance);
            }
            if ((y > other.getY() && velY < 0)||(y < other.getY() && velY > 0)) {
                velY = other.getVelY() - (velY * Math.abs(y-other.getY())/distance);
            }
            x = other.getX()+(x-other.getX())*(size+ other.getSize())/(float)distance;
            y = other.getY()+(y-other.getY())*(size+ other.getSize())/(float)distance;
        }
    }

    void hitPlayer(Player player, Upgrades upgrades, GameScreen gameScreen){
        if(upgrades.scoreLarger(player.getHealth(), "0")){
            player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.35,lvl)*10)));
        }
        gameScreen.removeMeteor(this);
    }

    void setTextColor(Paint paint){
        paint.setColor(Color.BLACK);
    }

    void setOutlineColor(Paint paint){
        paint.setColor(Color.GREEN);
    }
}
