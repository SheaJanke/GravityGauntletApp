package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
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
    private Bitmap laser_cannon;
    private Bitmap black_hole_generator;
    private Bitmap black_hole;
    private int blackHoleRotation = 0;

    Guns(Player player, Upgrades upgrades, Data data, Bitmap laser_cannon, Bitmap black_hole_generator, Bitmap black_hole){
        this.player = player;
        this.upgrades = upgrades;
        this.data = data;
        this.laser_cannon = laser_cannon;
        this.black_hole_generator = black_hole_generator;
        this.black_hole = black_hole;
        gunLvl = 0;

    }

    void tick(ArrayList<Bullets> addBullets){
        if (gunLvl == 0) {
            rotation += 3;
        } else if (gunLvl == 1||gunLvl == 4) {
            rotation += 2;
        } else if (gunLvl == 2) {
            rotation++;
        } else if(System.currentTimeMillis()-shootTimer>150){
            rotation += 2;
        }

        if(rotation>360){
            rotation-=360;
        }
        blackHoleRotation+=2;
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
            canvas.drawRect(player.getX()-X(65),player.getY()-Y(40),player.getX()+X(65),player.getY()+Y(40),paint);
            canvas.drawRect(player.getX()-X(20),player.getY()+Y(40),player.getX()+X(20),player.getY()+Y(60),paint);
            paint.setColor(Color.CYAN);
            canvas.drawRect(player.getX()-X(50),player.getY()+Y(55),player.getX()+X(50),player.getY()+Y(70),paint);
            paint.setARGB(255,212,175,55);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            canvas.drawRect(player.getX()-X(40),player.getY()-Y(50),player.getX()+X(40),player.getY()+Y(50),paint);
            paint.setStrokeWidth(X(5));
            paint.setColor(Color.WHITE);
            canvas.drawRect(player.getX()-X(65),player.getY()-Y(40),player.getX()+X(65),player.getY()+Y(40),paint);
            canvas.drawRect(player.getX()-X(35),player.getY()-Y(30),player.getX()+X(35),player.getY()+Y(30),paint);
            canvas.drawRect(player.getX()-X(15),player.getY()+Y(30),player.getX()+X(15),player.getY()+Y(50),paint);
            paint.setARGB(255,212,175,55);
            canvas.drawRect(player.getX()-X(50),player.getY()+Y(55),player.getX()+X(50),player.getY()+Y(70),paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.CYAN);
            canvas.drawRect(player.getX()-X(45),player.getY()-Y(40),player.getX()+X(45),player.getY()+Y(40),paint);
            paint.setColor(Color.WHITE);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(30),paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setARGB(255,212,175,55);
            canvas.drawRect(player.getX()-X(30),player.getY()-Y(100),player.getX()+X(30),player.getY()+Y(30),paint);
            paint.setStrokeWidth(X(15));
            canvas.drawLine(player.getX(),player.getY()+Y(20),player.getX(),player.getY()-Y(90),paint);
            canvas.restoreToCount(saveCount);
        }else if(gunLvl == 4){
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawBitmap(Bitmap.createScaledBitmap(laser_cannon,(int)X(240),(int)X(200),true),player.getX()-X(120),player.getY()-X(140),paint);
            canvas.restoreToCount(saveCount);
        }else  if(gunLvl == 5){
            canvas.rotate(rotation+90,player.getX(),player.getY());
            canvas.drawBitmap(Bitmap.createScaledBitmap(black_hole_generator,(int)X(260),(int)X(260),true),player.getX()-X(120),player.getY()-X(200),paint);
            int holeWidth;
            int holeHeight;
            if(System.currentTimeMillis()-shootTimer>1000){
                holeWidth = (int)X(80);
                holeHeight = (int)X(90);
            }else{
                holeWidth = (int)(X(79) * (System.currentTimeMillis()-shootTimer)/1000)+1;
                holeHeight = (int)(X(89) * (System.currentTimeMillis()-shootTimer)/1000)+1;
            }
            canvas.rotate(blackHoleRotation, player.getX()+X(7.5f), player.getY()-X(80));
            canvas.drawBitmap(Bitmap.createScaledBitmap(black_hole, holeWidth,holeHeight,true),player.getX()+X(7.5f)-holeWidth/2f,player.getY()-X(80)-holeHeight/2f,paint);

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
        }else if((gunLvl == 4 || gunLvl == 5) && System.currentTimeMillis()-shootTimer>1000 && ammo>0) {
            individualShot(addBullets);
            shootTimer = System.currentTimeMillis();
        }
    }

    private void individualShot(ArrayList<Bullets> addBullets){
        if((gunLvl == 0 || gunLvl == 4 || gunLvl == 5) && ammo > 0){
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
        if(gunLvl<3) {
            burst = Double.parseDouble(upgrades.getGunUnique(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(2, 3))]);
        }
        shootTimer = System.currentTimeMillis();
    }

    void tutorialReset(){
        gunLvl = 0;
        ammo = 100;
        burst = 1;
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

    int getExplosionRadius(){
        return Integer.parseInt(upgrades.getGunUnique(3)[Integer.parseInt(data.getGunLvls(3).substring(2,3))]);
    }

    int getLaserDuration(){
        return Integer.parseInt(upgrades.getGunUnique(4)[Integer.parseInt(data.getGunLvls(4).substring(2,3))]);
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

    int getGunLvl(){
        return gunLvl;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }

    int getBlackHoleRotation(){
        return blackHoleRotation;
    }


}
