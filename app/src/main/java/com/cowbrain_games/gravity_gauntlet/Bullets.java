package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.LinkedList;

class Bullets {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float velX;
    private float velY;
    private float x;
    private float y;
    private float rotation;
    private float rotationX;
    private float rotationY;

    Bullets(Player player, Guns guns){
        rotation = guns.getRotation();
        x = player.getX() +(float)Math.cos(rotation*Math.PI/180)*Y(80);
        y = player.getY() +(float)Math.sin(rotation*Math.PI/180)*Y(80);
        rotationX = player.getX();
        rotationY = player.getY();
        velX = (float)Math.cos(rotation*Math.PI/180)*X(10);
        velY = (float)Math.sin(rotation*Math.PI/180)*X(10);
    }

    void tick(GameScreen gameScreen, LinkedList<Meteor> meteors, Upgrades upgrades){
        y+=velY;
        x+=velX;
        for(Meteor meteor: meteors){
            if(Math.sqrt(Math.pow(x-meteor.getX(),2)+ Math.pow(y-meteor.getY(),2))<meteor.getSize()+X(15)){
                meteor.setHealth(upgrades.subtractScore(meteor.getHealth(),"1"));
                if(upgrades.scoreLarger("0.1",meteor.getHealth())){
                    gameScreen.removeMeteor(meteor);
                }
                gameScreen.removeBullet(this);
            }
        }

        if(y<Y(500)-X(2500)){
            gameScreen.removeBullet(this);
        }
    }

    void render(Canvas canvas,Player player){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x,y,X(15),paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(X(10));
        paint.setARGB(255,212,175,55);
        canvas.drawCircle(x,y,X(15),paint);

    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
