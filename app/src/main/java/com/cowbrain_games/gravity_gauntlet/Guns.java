package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;

class Guns {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Player player;
    private int rotation = 0;

    Guns(Player player){
        this.player = player;
    }

    void tick(){
        rotation+=3;
        if(rotation>360){
            rotation-=360;
        }
    }

    void render(Canvas canvas, int gunLvl){
        Paint paint = new Paint();
        int saveCount = canvas.save();
        if(gunLvl == 1){
            paint.setColor(Color.WHITE);
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY(),paint);
            paint.setColor(Color.CYAN);
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            paint.setARGB(255,212,175,55);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY(),paint);
            canvas.restoreToCount(saveCount);
            paint.setColor(Color.BLACK);
        }

    }
    void shoot(ArrayList<Bullets> addBullets, int gunLvl){
        if(gunLvl == 1){
            addBullets.add(new Bullets(player,this));
        }
    }

    int getRotation(){
        return  rotation;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }


}
