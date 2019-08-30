package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.ArrayList;
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
    private int gunLvl;

    Guns(Player player, Upgrades upgrades, Data data){
        this.player = player;
        this.upgrades = upgrades;
        this.data = data;
        gunLvl = 0;

    }

    void tick(ArrayList<Bullets> addBullets){
        if(gunLvl == 0){
            rotation+=3;
        }else if(gunLvl == 1){
            rotation+=2;
        }else if(gunLvl == 2){
            rotation++;
        }else{
            rotation+=2;
        }
        if(rotation>360){
            rotation-=360;
        }
        if(burstCounter<burst){
            individualShot(addBullets);
        }
    }

    void render(Canvas canvas){
        Paint paint = new Paint();
        int saveCount = canvas.save();
        if(gunLvl == 0){
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
        }else if(gunLvl == 1){
            paint.setColor(Color.WHITE);
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(100),paint);
            paint.setColor(Color.CYAN);
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            paint.setARGB(255,212,175,55);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawLine(player.getX(), player.getY()-Y(50),player.getX(),player.getY()+Y(50),paint);
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(100),paint);
            canvas.restoreToCount(saveCount);
            paint.setColor(Color.BLACK);
        }else if(gunLvl == 2){
            paint.setColor(Color.WHITE);
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(100),paint);
            canvas.drawRect(player.getX()-Y(100),player.getY()-X(30),player.getX()+Y(100),player.getY()+X(30),paint);
            paint.setColor(Color.CYAN);
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            paint.setARGB(255,212,175,55);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawLine(player.getX(), player.getY()-Y(60),player.getX(),player.getY()+Y(60),paint);
            canvas.drawLine(player.getX()-Y(60), player.getY(),player.getX()+Y(60),player.getY(),paint);
            canvas.drawCircle(player.getX(),player.getY(),X(30),paint);
            canvas.drawRect(player.getX()-Y(100),player.getY()-X(30),player.getX()+Y(100),player.getY()+X(30),paint);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(100),paint);
            canvas.restoreToCount(saveCount);
            paint.setColor(Color.BLACK);
        }else if(gunLvl==3){
            paint.setARGB(255,212,175,55);
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawRect(player.getX()-X(50),player.getY()-Y(30),player.getX()+X(50),player.getY()+Y(30),paint);
            canvas.drawRect(player.getX()-X(15),player.getY()+Y(30),player.getX()+X(15),player.getY()+Y(50),paint);
            paint.setColor(Color.CYAN);
            canvas.drawRect(player.getX()-X(40),player.getY()+Y(50),player.getX()+X(40),player.getY()+Y(60),paint);
            paint.setARGB(255,212,175,55);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(40),player.getX()+X(30),player.getY()+Y(40),paint);
            paint.setStrokeWidth(X(5));
            paint.setColor(Color.WHITE);
            canvas.drawRect(player.getX()-X(50),player.getY()-Y(30),player.getX()+X(50),player.getY()+Y(30),paint);
            canvas.drawRect(player.getX()-X(35),player.getY()-Y(30),player.getX()+X(35),player.getY()+Y(30),paint);
            canvas.drawRect(player.getX()-X(15),player.getY()+Y(30),player.getX()+X(15),player.getY()+Y(50),paint);
            paint.setARGB(255,212,175,55);
            canvas.drawRect(player.getX()-X(40),player.getY()+Y(50),player.getX()+X(40),player.getY()+Y(60),paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.CYAN);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(40),player.getX()+X(30),player.getY()+Y(40),paint);
            paint.setColor(Color.WHITE);
            canvas.drawRect(player.getX()-X(20),player.getY()-Y(90),player.getX()+X(20),player.getY()+Y(30),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setARGB(255,212,175,55);
            canvas.drawRect(player.getX()-X(20),player.getY()-Y(90),player.getX()+X(20),player.getY()+Y(30),paint);
            paint.setStrokeWidth(X(10));
            canvas.drawLine(player.getX(),player.getY()+Y(20),player.getX(),player.getY()-Y(80),paint);
            canvas.restoreToCount(saveCount);
        }

    }
    void shoot(ArrayList<Bullets> addBullets){
        if(gunLvl == 0 && System.currentTimeMillis()-shootTimer>500 && ammo>0){
            burstCounter=0;
            individualShot(addBullets);
            shootTimer = System.currentTimeMillis();
        }else if(gunLvl == 1 && System.currentTimeMillis()-shootTimer>400 && ammo>0) {
            burstCounter=0;
            individualShot(addBullets);
            shootTimer = System.currentTimeMillis();
        }else if(gunLvl == 2 && System.currentTimeMillis()-shootTimer>300 && ammo>0) {
            burstCounter=0;
            individualShot(addBullets);
            shootTimer = System.currentTimeMillis();
        }else if(gunLvl == 3 && System.currentTimeMillis()-shootTimer>500 && ammo>0) {
            individualShot(addBullets);
            shootTimer = System.currentTimeMillis();
        }
    }

    private void individualShot(ArrayList<Bullets> addBullets){
        if(gunLvl == 0 && ammo > 0){
            burstCounter++;
            ammo--;
            addBullets.add(new Bullets(player, this,0,gunLvl));
        }else if(gunLvl == 1 && ammo > 0){
            burstCounter++;
            ammo-=2;
            addBullets.add(new Bullets(player, this,0,gunLvl));
            addBullets.add(new Bullets(player, this,180,gunLvl));
        }else if(gunLvl == 2 && ammo > 0){
            burstCounter++;
            ammo-=4;
            addBullets.add(new Bullets(player, this,0,gunLvl));
            addBullets.add(new Bullets(player, this,90,gunLvl));
            addBullets.add(new Bullets(player, this,180,gunLvl));
            addBullets.add(new Bullets(player, this,270,gunLvl));
        }else if(gunLvl == 3 && ammo > 0){
            burstCounter++;
            ammo-=1;
            addBullets.add(new Bullets(player, this,0,gunLvl));
        }
    }

    void gameReset(){
        gunLvl = data.getAllGunPurchases().indexOf("2");
        ammo = Double.parseDouble(upgrades.getGunAmmo(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(0,1))]);
        burst = Double.parseDouble(upgrades.getGunUnique(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(2,3))]);
        shootTimer = System.currentTimeMillis();
    }

    void reset(){
        ammo = Double.parseDouble(upgrades.getGunAmmo(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(0,1))]);
        if(gunLvl<3) {
            burst = Double.parseDouble(upgrades.getGunUnique(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(2, 3))]);
        }
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

    void setGunLvl(int gunLvl){
        this.gunLvl = gunLvl;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }


}
