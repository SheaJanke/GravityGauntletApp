package com.cowbrain_games.gravity_gauntlet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private float rotation;
    private int gunLvl;
    boolean exploded = false;
    float maxExplosionRadius = 100;
    float explosionRadius = 5;
    int explosionAlpha = 255;

    Bullets(Player player, Guns guns, int degrees, int gunLvl){
        this.gunLvl = gunLvl;
        rotation = guns.getRotation();
        x = player.getX() +(float)Math.cos((rotation+degrees)*Math.PI/180)*Y(120);
        y = player.getY() +(float)Math.sin((rotation+degrees)*Math.PI/180)*Y(120);
        velX = (float)Math.cos((rotation+degrees)*Math.PI/180)*X(10);
        velY = (float)Math.sin((rotation+degrees)*Math.PI/180)*X(10);
    }

    void tick(LinkedList<Meteor> meteors, Upgrades upgrades, Data data, ArrayList<Bullets> removeBullet, ArrayList<Meteor> removeMeteor){
        if(!exploded) {
            y += velY;
            x += velX;
            for (Meteor meteor : meteors) {
                if (Math.sqrt(Math.pow(x - meteor.getX(), 2) + Math.pow(y - meteor.getY(), 2)) < meteor.getSize() + X(15)) {
                    meteor.setHealth(upgrades.subtractScore(meteor.getHealth(), upgrades.getGunDamage(gunLvl)[Integer.parseInt(data.getGunLvls(gunLvl).substring(1, 2))]));
                    if (upgrades.scoreLarger("0.1", meteor.getHealth())) {
                        removeMeteor.add(meteor);
                    }
                    if(gunLvl == 3){
                        exploded = true;
                    }else{
                        removeBullet.add(this);
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
            }
        }
    }

    void render(Canvas canvas, Bitmap grenade){
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
        }else {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, X(15), paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(X(10));
            paint.setARGB(255, 212, 175, 55);
            canvas.drawCircle(x, y, X(15), paint);
        }


    }

    private float X(float X){
        return X * width/2000f;
    }

    private float Y(float Y){
        return Y* height/1000f;
    }
}
