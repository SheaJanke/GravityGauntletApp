package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;

class Bullets {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float velX;
    private float velY;
    private float x;
    private float y;
    private float rotation;
    private int gunLvl;

    Bullets(Player player, Guns guns, int degrees, int gunLvl){
        this.gunLvl = gunLvl;
        rotation = guns.getRotation();
        x = player.getX() +(float)Math.cos((rotation+degrees)*Math.PI/180)*Y(100);
        y = player.getY() +(float)Math.sin((rotation+degrees)*Math.PI/180)*Y(100);
        velX = (float)Math.cos((rotation+degrees)*Math.PI/180)*X(10);
        velY = (float)Math.sin((rotation+degrees)*Math.PI/180)*X(10);
    }

    void tick(LinkedList<Meteor> meteors, Upgrades upgrades, Data data, ArrayList<Bullets> removeBullet, ArrayList<Meteor> removeMeteor){
        y+=velY;
        x+=velX;
        for(Meteor meteor: meteors){
            if(Math.sqrt(Math.pow(x-meteor.getX(),2)+ Math.pow(y-meteor.getY(),2))<meteor.getSize()+X(15)){
                meteor.setHealth(upgrades.subtractScore(meteor.getHealth(),upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1,2))]));
                if(upgrades.scoreLarger("0.1",meteor.getHealth())){
                    removeMeteor.add(meteor);
                }
                removeBullet.add(this);
            }
        }

        if(x>X(2100) || x<X(-100) || y>Y(1100)||y<Y(-100)){
            removeBullet.add(this);
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
