package com.cowbraingames.gravitygauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;

public class Meteor {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Upgrades upgrades;
    private int lvl;
    private float x = X((float)Math.random()*2000);
    private float y = Y((float)Math.random()*1000);
    private double accelX;
    private double accelY;
    private double accel;
    private double velX = 0;
    private double velY = 0;
    private double vel = 0;
    private int size;
    private boolean alive = true;

    public Meteor(int size, Upgrades upgrades){
        this.size = size;
        this.upgrades = upgrades;
    }
    public void tick(Player player, LinkedList<Meteor> others, GameScreen gameScreen){
        //determines the velocity of the meteor based on its position relative to the player
        if(player.getX() == x && player.getY() == y){
            return;
        }
        double radius = Math.sqrt(Math.pow(player.getX()-x, 2) + Math.pow(player.getY()-y, 2));
        accel = player.getWeight()/(radius * size);
        accelX = accel * ((player.getX()-x)/radius);
        accelY = accel * ((player.getY()-y)/radius);
        velX += accelX;
        velY += accelY;
        if((x<X(0) && velX < 0)||(x > X(2000)-size && velX > 0)){
            velX = -velX/1.25;
            velY = velY/1.25;
        }
        if((y<Y(0) && velY < 0)||(y > Y(1000)-size && velY > 0)){
            velY = -velY/1.25;
            velX = velX/1.25;
        }
        x+= velX;
        y+= velY;

        if(distanceFromPlayer(player) < (size + player.getSize())){
            /*if(upgrades.scoreLarger(player.getHealth(), "0")){
                player.setHealth(upgrades.subtractScore(player.getHealth(), Double.toString(Math.pow(1.5, lvl))));
            }*/
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

    public void render(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x,y,size,paint);


    }
    public double distanceFromPlayer(Player player){
        double sumX = player.getX() - getX();
        double sumY = player.getY() - getY();
        return Math.sqrt(Math.pow(sumX, 2) + Math.pow(sumY, 2));
    }

    private double squareDistanceFromMeteor(Meteor meteor){
        double sumX = meteor.getX() - this.getX();
        double sumY = meteor.getY() - this.getY();
        return Math.pow(sumX, 2) + Math.pow(sumY, 2);
    }

    /*private Color getBallColor(){
        return upgrades.getBallColor()[lvl%10];
    }

    private Color getOutlineColor(){
        return upgrades.getOutlineColor()[lvl/10];
    }*/

    public boolean getAlive(){
        return alive;
    }

    public int getLvl(){
        return lvl;
    }

    public double velAngle(){
        return Math.atan(-velY/velX);
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public int getSize() {
        return size;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
