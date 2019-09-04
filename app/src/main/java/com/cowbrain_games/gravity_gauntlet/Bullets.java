package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import java.util.ArrayList;
import java.util.LinkedList;

class Bullets {
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    private ArrayList<Meteor> meteorsExploded = new ArrayList<Meteor>();
    private float velX;
    private float velY;
    private float x;
    private float y;
    private int blackHoleRotation;
    private float rotation;
    private int gunLvl;
    boolean exploded = false;
    private float maxExplosionRadius;
    float explosionRadius = 5;
    int explosionAlpha = 255;
    private long laserTimer = System.currentTimeMillis();
    private Guns guns;
    private Player player;
    private int[] hitboxRadius = {15,15,15,20,10,35};

    Bullets(Player player, Guns guns, int degrees, int gunLvl){
        this.player = player;
        this.gunLvl = gunLvl;
        this.guns = guns;
        maxExplosionRadius = guns.getExplosionRadius();
        rotation = guns.getRotation();
        if(gunLvl == 4){
            x = player.getX();
            y = player.getY();
        }else if(gunLvl == 5) {
            x = player.getX() + X(7.5f) + (float) Math.cos((rotation + degrees) * Math.PI / 180) * Y(80);
            y = player.getY() + (float) Math.sin((rotation + degrees) * Math.PI / 180) * Y(80);
        }else{
            x = player.getX() + (float) Math.cos((rotation + degrees) * Math.PI / 180) * Y(120);
            y = player.getY() + (float) Math.sin((rotation + degrees) * Math.PI / 180) * Y(120);
        }
        if(gunLvl == 5){
            velX = (float)Math.cos((rotation+degrees)*Math.PI/180)*X(5);
            velY = (float)Math.sin((rotation+degrees)*Math.PI/180)*X(5);
        }else if(gunLvl == 3){
            velX = (float) Math.cos((rotation + degrees) * Math.PI / 180) * X(15);
            velY = (float) Math.sin((rotation + degrees) * Math.PI / 180) * X(15);
        }else{
            velX = (float) Math.cos((rotation + degrees) * Math.PI / 180) * X(10);
            velY = (float) Math.sin((rotation + degrees) * Math.PI / 180) * X(10);
        }
        if(gunLvl==5){
            blackHoleRotation = guns.getBlackHoleRotation();
        }
    }

    void tick(LinkedList<Meteor> meteors, Upgrades upgrades, Data data, ArrayList<Bullets> removeBullet, ArrayList<Meteor> removeMeteor){
        if(!exploded) {
            if(gunLvl == 4){
                rotation = guns.getRotation();
                x = player.getX();
                y = player.getY();
                if(System.currentTimeMillis()-laserTimer > 300){
                    removeBullet.add(this);
                }
            }else{
                y += velY;
                x += velX;
            }
            for (Meteor meteor : meteors) {
                if(gunLvl == 4){
                    for(int a = 10; a < 1000; a+=5){
                        double ax = x + a*Math.cos(rotation*Math.PI/180);
                        double ay = y + a*Math.sin(rotation*Math.PI/180);
                        if(Math.sqrt(Math.pow(meteor.getX()-ax,2) + Math.pow(meteor.getY()-ay,2))<X(10)+ meteor.getSize()) {
                            meteor.setHealth(upgrades.subtractScore(meteor.getHealth(), upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1, 2))]));
                            if (upgrades.scoreLarger("0.1", meteor.getHealth())) {
                                removeMeteor.add(meteor);
                            }
                        }
                    }
                }else {
                    if (Math.sqrt(Math.pow(x - meteor.getX(), 2) + Math.pow(y - meteor.getY(), 2)) < meteor.getSize() + hitboxRadius[gunLvl]) {
                        meteor.setHealth(upgrades.subtractScore(meteor.getHealth(), upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1, 2))]));
                        if (upgrades.scoreLarger("0.1", meteor.getHealth())) {
                            removeMeteor.add(meteor);
                        }
                        if (gunLvl == 3||gunLvl==5) {
                            exploded = true;
                        } else {
                            removeBullet.add(this);
                        }
                    }
                }
            }

            if (x > X(2100) || x < X(-100) || y > Y(1100) || y < Y(-100)) {
                removeBullet.add(this);
            }
        }else{
            if(gunLvl == 3){
                for(Meteor meteor: meteors){
                    if (Math.sqrt(Math.pow(x - meteor.getX(), 2) + Math.pow(y - meteor.getY(), 2)) < meteor.getSize() + explosionRadius && !meteorsExploded.contains(meteor)){
                        meteor.setHealth(upgrades.subtractScore(meteor.getHealth(), upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1, 2))]));
                        meteorsExploded.add(meteor);
                        if (upgrades.scoreLarger("0.1", meteor.getHealth())) {
                            removeMeteor.add(meteor);
                        }
                    }
                }
                if(explosionRadius<maxExplosionRadius)
                    explosionRadius+=maxExplosionRadius/20;
                    explosionAlpha-=5;
                if(explosionAlpha <=0){
                    removeBullet.add(this);
                }
            }else if(gunLvl == 5){
                for(Meteor meteor: meteors){
                    double distance = Math.sqrt(Math.pow(x - meteor.getX(), 2) + Math.pow(y - meteor.getY(), 2));
                    if (distance < meteor.getSize() + explosionRadius){
                        meteor.setHealth(upgrades.subtractScore(meteor.getHealth(), upgrades.multiplyScore(upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1, 2))],(meteor.getSize() + explosionRadius-distance)/(meteor.getSize() + explosionRadius))));
                        if (upgrades.scoreLarger("0.1", meteor.getHealth())) {
                            removeMeteor.add(meteor);
                        }
                    }
                }
                if(explosionRadius<X(100)) {
                    explosionRadius += X(10);
                    laserTimer = System.currentTimeMillis();
                }
                if(System.currentTimeMillis()-laserTimer>3000){
                    removeBullet.add(this);
                }
            }
        }
        blackHoleRotation+=2;
    }

    void render(Canvas canvas, Bitmap grenade, Bitmap black_hole){
        Paint paint = new Paint();
        int saveCount = canvas.save();
        if(gunLvl==3){
            if(!exploded) {
                canvas.rotate(rotation - 90, x, y);
                canvas.drawBitmap(Bitmap.createScaledBitmap(grenade, (int) X(80), (int) X(80), true), x - X(30), y - X(30), paint);
                canvas.restoreToCount(saveCount);
            }else{
                paint.setShader(new RadialGradient(x,y,explosionRadius,Color.BLACK, Color.RED, Shader.TileMode.REPEAT));
                paint.setAlpha(explosionAlpha);
                canvas.drawCircle(x,y,explosionRadius,paint);
                paint.setShader(new Shader());
            }
        }else if(gunLvl == 4){
            canvas.rotate(rotation + 90, x, y);
            paint.setAlpha(100);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(X(20));
            canvas.drawLine(x,y,x,0-X(1000),paint);
            canvas.restoreToCount(saveCount);
        }else if(gunLvl==5){
            if(!exploded) {
                canvas.rotate(blackHoleRotation, x, y);
                canvas.drawBitmap(Bitmap.createScaledBitmap(black_hole, (int) X(80), (int) X(90), true), x - X(40), y - X(45), paint);
                canvas.restoreToCount(saveCount);
            }else{
                canvas.rotate(blackHoleRotation, x, y);
                canvas.drawBitmap(Bitmap.createScaledBitmap(black_hole, (int) (X(80)+explosionRadius), (int)(X(90)+explosionRadius), true), x- (int) (X(80)+explosionRadius)/2f, y - (int)(X(90)+explosionRadius)/2f, paint);
                canvas.restoreToCount(saveCount);
            }
        }else {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, X(15), paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            paint.setARGB(255, 212, 175, 55);
            canvas.drawCircle(x, y, X(15), paint);
        }


    }

    float getX(){
        return x;
    }
    float getY(){
        return y;
    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
