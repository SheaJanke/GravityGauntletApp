package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

class Guns {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private long shootTimer = System.currentTimeMillis();
    private double ammo;
    private double burst;
    private int burstCounter = 0;
    private Player player;
    private Upgrades upgrades;
    private Data data;
    private int rotation = 0;

    Guns(Player player, Upgrades upgrades, Data data){
        this.player = player;
        this.upgrades = upgrades;
        this.data = data;

    }

    void tick(ArrayList<Bullets> addBullets, int gunLvl){
        rotation+=3;
        if(rotation>360){
            rotation-=360;
        }
        if(burstCounter<burst){
            individualShot(addBullets, gunLvl);
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
        if(gunLvl == 1 && System.currentTimeMillis()-shootTimer>500 && ammo>0){
            burstCounter=0;
            individualShot(addBullets,gunLvl);
            shootTimer = System.currentTimeMillis();
        }
    }

    private void individualShot(ArrayList<Bullets> addBullets, int gunLvl){
        if(gunLvl == 1 && ammo > 0){
            burstCounter++;
            ammo--;
            addBullets.add(new Bullets(player, this));
        }
    }

    void reset(){
        ammo = Double.parseDouble(upgrades.getGunAmmo(0)[Integer.parseInt(data.getGun1Lvls().substring(0,1))]);
        burst = Double.parseDouble(upgrades.getGunBurst(0)[Integer.parseInt(data.getGun1Lvls().substring(2,3))]);
        shootTimer = System.currentTimeMillis();
    }

    int getRotation(){
        return  rotation;
    }

    void setAmmo(int ammo){
        this.ammo = ammo;
    }

    String getAmmo(){
        return String.format(Locale.getDefault(),"%.0f",ammo);
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }


}
